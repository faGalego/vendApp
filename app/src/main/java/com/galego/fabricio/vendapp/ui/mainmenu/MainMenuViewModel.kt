package com.galego.fabricio.vendapp.ui.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.data.db.wrapper.MonthWithTotal
import com.galego.fabricio.vendapp.repository.OrderRepository
import kotlinx.coroutines.launch

class MainMenuViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _allTotalsByMonths = MutableLiveData<List<MonthWithTotal?>>()
    val allTotalsByMonths: LiveData<List<MonthWithTotal?>> get() = _allTotalsByMonths

    fun getAllTotalsByMonths() = viewModelScope.launch {
        _allTotalsByMonths.postValue(orderRepository.getTotalGroupingByDate())
    }

}