package com.galego.fabricio.vendapp.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity
import com.galego.fabricio.vendapp.repository.CustomerRepository
import com.galego.fabricio.vendapp.repository.OrderProductRepository
import com.galego.fabricio.vendapp.repository.OrderRepository
import com.galego.fabricio.vendapp.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _orderStateEventData = MutableLiveData<OrderState>()
    val orderStateEventData: LiveData<OrderState> get() = _orderStateEventData

    private val _orderMessageEventData = MutableLiveData<Int>()
    val orderMessageEventData: LiveData<Int> get() = _orderMessageEventData

    private val _productsData = MutableLiveData<MutableList<OrderProductEntity>>()
    val productsData: LiveData<MutableList<OrderProductEntity>> get() = _productsData

    private val _allCustomersEventData = MutableLiveData<List<CustomerEntity>>()
    val allCustomersEventData: LiveData<List<CustomerEntity>> get() = _allCustomersEventData

    private var _selectedCustomer: CustomerEntity? = null
    private var _orderProductsToDelete: MutableList<Long> = mutableListOf()

    fun getProductsByOrderId(orderId: Long) = viewModelScope.launch {
        if (orderId > 0) {
            _productsData.postValue(orderProductRepository.getByOrderId(orderId).toMutableList())
        }
    }

    fun insertProduct(productId: Long) = viewModelScope.launch {

        if (productId > 0) {
            val product = productRepository.getProductById(productId)
            if (product == null) {
                _orderMessageEventData.value = R.string.order_error_product_not_found
            } else {
                addProductToList(product)
            }
            _orderStateEventData.value = OrderState.TriedToInsertProduct
        }
    }

    private fun addProductToList(product: ProductEntity) {
        val orderProduct = OrderProductEntity(
            productId = product.id,
            quantity = 1,
            price = product.price,
            total = product.price * 1
        )

        if (_productsData.value == null) {
            _productsData.value = mutableListOf()
        }
        _productsData.value?.add(orderProduct)
        _productsData.postValue(_productsData.value)
    }

    fun removeProduct(product: OrderProductEntity) {
        if (product.id > 0) {
            _orderProductsToDelete.add(product.id)
        }
        _productsData.value?.remove(product)
        _productsData.postValue(_productsData.value)
    }

    fun oneMore(product: OrderProductEntity) {
        val currentList = _productsData.value ?: return
        val existingProduct = currentList.find { it == product }

        if (existingProduct != null) {
            existingProduct.quantity += 1
            existingProduct.total = existingProduct.quantity * existingProduct.price
            _productsData.postValue(currentList)
        }
    }

    fun oneLess(product: OrderProductEntity) {
        val currentList = _productsData.value ?: return
        val existingProduct = currentList.find { it == product }

        if (existingProduct != null && existingProduct.quantity > 1) {
            existingProduct.quantity -= 1
            existingProduct.total = existingProduct.quantity * existingProduct.price
            _productsData.postValue(currentList)
        }
    }

    fun getAllCustomers() = viewModelScope.launch {
        _allCustomersEventData.postValue(customerRepository.getAllCustomers())
    }

    fun setSelectedCustomer(position: Int) {
        _selectedCustomer = _allCustomersEventData.value?.get(position)
    }

    fun insertOrUpdateOrder(id: Long) {
        if (_productsData.value?.isEmpty() == true) {
            _orderMessageEventData.value = R.string.order_error_no_items
        } else {
            if (id > 0) {
                updateOrder(id)
            } else {
                insertOrder()
            }
        }
    }

    private fun updateOrder(id: Long) =
        viewModelScope.launch {
            try {
                orderRepository.updateOrder(
                    id,
                    _selectedCustomer!!.id,
                    getOrderTotal(),
                    Calendar.getInstance().time
                )
                saveProductsOnDb(id)
                _orderStateEventData.value = OrderState.Updated
                _orderMessageEventData.value = R.string.order_updated_successfully
            } catch (e: Exception) {
                _orderMessageEventData.value = R.string.order_error_to_update
                Log.e(TAG, e.toString())
            }
        }

    private fun insertOrder() =
        viewModelScope.launch {
            try {
                val id = orderRepository.insertOrder(
                    _selectedCustomer!!.id,
                    getOrderTotal(),
                    Calendar.getInstance().time
                )
                if (id > 0) {
                    saveProductsOnDb(id)
                    _orderStateEventData.value = OrderState.Inserted
                    _orderMessageEventData.value = R.string.order_inserted_successfully
                }
            } catch (e: Exception) {
                _orderMessageEventData.value = R.string.order_error_to_insert
                Log.e(TAG, e.toString())
            }
        }

    private fun saveProductsOnDb(orderId: Long) {
        _productsData.value!!.forEach { item ->
            item.orderId = orderId
            if (item.id > 0) {
                updateProduct(item)
            } else {
                insertProduct(item)
            }
        }
        _orderProductsToDelete.forEach { id ->
            deleteProduct(id)
        }
    }

    private fun updateProduct(orderProduct: OrderProductEntity) = viewModelScope.launch {
        orderProductRepository.update(orderProduct)
    }

    private fun insertProduct(orderProduct: OrderProductEntity) = viewModelScope.launch {
        orderProductRepository.insert(orderProduct)
    }

    private fun deleteProduct(id: Long) = viewModelScope.launch {
        orderProductRepository.delete(id)
    }

    private fun getOrderTotal(): Double {
        return _productsData.value!!.toList().sumOf { it.total };
    }

    sealed class OrderState {
        object Inserted : OrderState()
        object Updated : OrderState()
        object TriedToInsertProduct : OrderState()
    }

    companion object {
        private val TAG = OrderViewModel::class.java.simpleName
    }

}