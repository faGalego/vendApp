package com.galego.fabricio.vendapp.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity
import com.galego.fabricio.vendapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allProductsEvent = MutableLiveData<List<ProductEntity>>()
    val allProductsEvent: LiveData<List<ProductEntity>>
        get() = _allProductsEvent

    fun getAllProducts() = viewModelScope.launch {
        _allProductsEvent.postValue(repository.getAllProducts())
    }

}