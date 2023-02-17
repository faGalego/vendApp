package com.galego.fabricio.vendapp.ui.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.repository.CustomerRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class CustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _customerStateEventData = MutableLiveData<CustomerState>()
    val customerStateEventData: LiveData<CustomerState> get() = _customerStateEventData

    private val _customerMessageEventData = MutableLiveData<Int>()
    val customerMessageEventData: LiveData<Int> get() = _customerMessageEventData

    fun insertOrUpdateCustomer(name: String, phone: String, id: Long, createdAt: Date) {
        if (id > 0) {
            updateCustomer(name, phone, id, createdAt)
        } else {
            insertCustomer(name, phone)
        }

    }

    private fun updateCustomer(name: String, phone: String, id: Long, createdAt: Date) =
        viewModelScope.launch {
            try {
                customerRepository.updateCustomer(id, name, phone, createdAt)
                _customerStateEventData.value = CustomerState.Updated
                _customerMessageEventData.value = R.string.customer_updated_successfully
            } catch (e: Exception) {
                _customerMessageEventData.value = R.string.product_error_to_update
                Log.e(TAG, e.toString())
            }
        }

    private fun insertCustomer(name: String, phone: String) = viewModelScope.launch {
        try {
            val id = customerRepository.insertCustomer(name, phone)
            if (id > 0) {
                _customerStateEventData.value = CustomerState.Inserted
                _customerMessageEventData.value = R.string.customer_inserted_successfully
            }
        } catch (e: Exception) {
            _customerMessageEventData.value = R.string.customer_error_to_insert
            Log.e(TAG, e.toString())
        }
    }

    sealed class CustomerState {
        object Inserted : CustomerState()
        object Updated : CustomerState()
    }

    companion object {
        private val TAG = CustomerViewModel::class.java.simpleName
    }

}