package com.galego.fabricio.vendapp.ui.customerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.repository.CustomerRepository
import kotlinx.coroutines.launch

class CustomerListViewModel(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _allCustomersEventData = MutableLiveData<List<CustomerEntity>>()
    val allCustomersEventData: LiveData<List<CustomerEntity>> get() = _allCustomersEventData

    fun getAllCustomers() = viewModelScope.launch {
        _allCustomersEventData.postValue(repository.getAllCustomers())
    }

}