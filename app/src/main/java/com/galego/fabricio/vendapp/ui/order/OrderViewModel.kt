package com.galego.fabricio.vendapp.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.repository.CustomerRepository
import com.galego.fabricio.vendapp.repository.OrderRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _orderStateEventData = MutableLiveData<OrderState>()
    val orderStateEventData: LiveData<OrderState> get() = _orderStateEventData

    private val _orderMessageEventData = MutableLiveData<Int>()
    val orderMessageEventData: LiveData<Int> get() = _orderMessageEventData

    private val _allCustomersEventData = MutableLiveData<List<CustomerEntity>>()
    val allCustomersEventData: LiveData<List<CustomerEntity>> get() = _allCustomersEventData

    private var _selectedCustomer: CustomerEntity? = null

    fun getAllCustomers() = viewModelScope.launch {
        _allCustomersEventData.postValue(customerRepository.getAllCustomers())
    }

    fun setSelectedCustomer(position: Int) {
        _selectedCustomer = _allCustomersEventData.value?.get(position)
    }

    fun insertOrUpdateOrder(total: Double, id: Long) {
        if (id > 0) {
            updateOrder(total, id)
        } else {
            insertOrder(total)
        }
    }

    private fun updateOrder(total: Double, id: Long) =
        viewModelScope.launch {
            try {
                orderRepository.updateOrder(id, _selectedCustomer!!.id, total)
                _orderStateEventData.value = OrderState.Updated
                _orderMessageEventData.value = R.string.order_updated_successfully
            } catch (e: Exception) {
                _orderMessageEventData.value = R.string.order_error_to_update
                Log.e(TAG, e.toString())
            }
        }

    private fun insertOrder(total: Double) =
        viewModelScope.launch {
            try {
                val id = orderRepository.insertOrder(_selectedCustomer!!.id, total)
                if (id > 0) {
                    _orderStateEventData.value = OrderState.Inserted
                    _orderMessageEventData.value = R.string.order_inserted_successfully
                }
            } catch (e: Exception) {
                _orderMessageEventData.value = R.string.order_error_to_insert
                Log.e(TAG, e.toString())
            }

        }

    sealed class OrderState {
        object Inserted : OrderState()
        object Updated : OrderState()
    }

    companion object {
        private val TAG = OrderViewModel::class.java.simpleName
    }

}