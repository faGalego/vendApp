package com.galego.fabricio.vendapp.ui.orderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.data.db.wrapper.OrderCustomer
import com.galego.fabricio.vendapp.repository.OrderCustomerRepository
import com.galego.fabricio.vendapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val repository: OrderCustomerRepository
) : ViewModel() {

    private val _allOrdersEventData = MutableLiveData<List<OrderCustomer>>()
    val allOrdersEventData: LiveData<List<OrderCustomer>> get() = _allOrdersEventData

    fun getAllOrders() = viewModelScope.launch {
        _allOrdersEventData.postValue(repository.getAllOrders())
    }

}