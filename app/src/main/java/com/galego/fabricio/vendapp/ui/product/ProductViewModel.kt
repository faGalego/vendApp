package com.galego.fabricio.vendapp.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productStateEventData = MutableLiveData<ProductState>()
    val productStateEventData: LiveData<ProductState>
        get() = _productStateEventData

    private val _productMessageEventData = MutableLiveData<Int>()
    val productMessageEventData: LiveData<Int>
        get() = _productMessageEventData

    fun insertOrUpdateProduct(name: String, price: Double, id: Long) {
        if (id > 0) {
            updateProduct(name, price, id)
        } else {
            insertProduct(name, price)
        }
    }

    private fun updateProduct(name: String, price: Double, id: Long) =
        viewModelScope.launch {
            try {
                repository.updateProduct(id, name, price)

                _productStateEventData.value = ProductState.Updated
                _productMessageEventData.value = R.string.product_updated_successfully

            } catch (e: Exception) {
                _productMessageEventData.value = R.string.product_error_to_update
                Log.e(TAG, e.toString())
            }
        }

    private fun insertProduct(name: String, price: Double) = viewModelScope.launch {
        try {
            val id = repository.insertProduct(name, price)
            if (id > 0) {
                _productStateEventData.value = ProductState.Inserted
                _productMessageEventData.value = R.string.product_inserted_successfully
            }
        } catch (e: Exception) {
            _productMessageEventData.value = R.string.product_error_to_insert
            Log.e(TAG, e.toString())
        }
    }

    sealed class ProductState {
        object Inserted : ProductState()
        object Updated : ProductState()
    }

    companion object {
        private val TAG = ProductViewModel::class.java.simpleName
    }

}