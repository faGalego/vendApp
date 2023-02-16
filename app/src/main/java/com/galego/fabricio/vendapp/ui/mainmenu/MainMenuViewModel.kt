package com.galego.fabricio.vendapp.ui.mainmenu

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.wrapper.BestSeller
import com.galego.fabricio.vendapp.data.db.wrapper.MonthWithTotal
import com.galego.fabricio.vendapp.repository.OrderRepository
import com.galego.fabricio.vendapp.repository.ProductRepository
import kotlinx.coroutines.launch
import java.util.*

class MainMenuViewModel(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _allTotalsByMonths = MutableLiveData<List<MonthWithTotal?>>()
    val allTotalsByMonths: LiveData<List<MonthWithTotal?>> get() = _allTotalsByMonths

    private val _currentMonth = MutableLiveData<Int>()
    val currentMonth: LiveData<Int> get() = _currentMonth

    private var stringMonth = ""

    private val _amountTotalsByMonth = MutableLiveData<Double>()
    val amountTotalsByMonth: LiveData<Double> get() = _amountTotalsByMonth

    private val _quantityTotalsByMonth = MutableLiveData<Int>()
    val quantityTotalsByMonth: LiveData<Int> get() = _quantityTotalsByMonth

    private val _countProducts = MutableLiveData<Int>()
    val countProducts: LiveData<Int> get() = _countProducts

    private val _bestSeller = MutableLiveData<BestSeller?>()
    val bestSeller: LiveData<BestSeller?> get() = _bestSeller

    fun getAllTotalsByMonths() = viewModelScope.launch {
        _allTotalsByMonths.postValue(orderRepository.getTotalGroupingByDate())
    }

    fun getOrdersBiInfo() {
        setMonth()
        getOrdersTitle()
        getAmountTotalByMonth()
        getQuantityTotalByMonth()
    }

    private fun setMonth() {
        stringMonth = Converters.monthToString(Calendar.getInstance().get(Calendar.MONTH))
    }

    private fun getOrdersTitle() {
        _currentMonth.value = when (stringMonth) {
            "01" -> R.string.mainmenu_january
            "02" -> R.string.mainmenu_february
            "03" -> R.string.mainmenu_march
            "04" -> R.string.mainmenu_april
            "05" -> R.string.mainmenu_may
            "06" -> R.string.mainmenu_june
            "07" -> R.string.mainmenu_july
            "08" -> R.string.mainmenu_august
            "09" -> R.string.mainmenu_september
            "10" -> R.string.mainmenu_october
            "11" -> R.string.mainmenu_november
            "12" -> R.string.mainmenu_december
            else -> 0
        }
    }

    private fun getAmountTotalByMonth() = viewModelScope.launch {
        _amountTotalsByMonth.postValue(orderRepository.getAmountTotalByMonth(stringMonth))
    }

    private fun getQuantityTotalByMonth() = viewModelScope.launch {
        _quantityTotalsByMonth.postValue(
            orderRepository.getCountOrdersByMonth(stringMonth)
        )
    }

    fun getProductsBiInfo() {
        getCountProducts()
        getBestSeller()
    }

    private fun getCountProducts() = viewModelScope.launch {
        _countProducts.postValue(productRepository.getCountProducts())
    }

    private fun getBestSeller() = viewModelScope.launch {
        _bestSeller.postValue(productRepository.getBestSeller())
    }

}