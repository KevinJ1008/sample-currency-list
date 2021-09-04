package com.kevinj1008.samplecurrencylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import com.kevinj1008.basecore.base.BaseViewModel
import com.kevinj1008.localclient.model.CurrencyInfo
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository

class CurrencyViewModel(
    private val repository: CurrencyRepository
) : BaseViewModel() {

    private val _update = MutableLiveData<Boolean>()
    val currencyList: LiveData<List<CurrencyInfo>> = _update.switchMap {
        repository.observeCurrencyList().distinctUntilChanged()
    }

    fun load() {
        _update.value = true
    }

    //TODO: sorting feature
}