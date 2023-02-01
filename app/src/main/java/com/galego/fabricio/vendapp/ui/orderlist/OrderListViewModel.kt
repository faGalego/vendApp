package com.galego.fabricio.vendapp.ui.orderlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import com.galego.fabricio.vendapp.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _allOrdersEventData = MutableLiveData<List<OrderEntity>>()
    val allOrdersEventData: LiveData<List<OrderEntity>> get() = _allOrdersEventData

    fun getAllOrders() = viewModelScope.launch {
        _allOrdersEventData.postValue(repository.getAllOrders())
    }

}