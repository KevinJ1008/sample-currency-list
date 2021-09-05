package com.kevinj1008.samplecurrencylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import com.kevinj1008.basecore.base.BaseViewModel
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository

class CurrencyViewModel(
    private val repository: CurrencyRepository
) : BaseViewModel() {

    private val _sort = MutableLiveData<SortOrder>()
    val currencyList: LiveData<List<CurrencyInfo>> = _sort.switchMap {
        when (it) {
            SortOrder.ASCENDING -> repository.observeCurrencyList().distinctUntilChanged() //TODO: change real func
            SortOrder.DESCENDING -> repository.observeCurrencyList().distinctUntilChanged() //TODO: change real func
            else -> repository.observeCurrencyList().distinctUntilChanged()
        }
    }

    fun load() {
        _sort.value = SortOrder.ORIGIN
    }

    //TODO: sorting feature
    fun sortByOrder(order: SortOrder) {
        _sort.value = order
    }
}