package com.kevinj1008.samplecurrencylist.viewmodel

import androidx.lifecycle.*
import com.kevinj1008.basecore.base.BaseViewModel
import com.kevinj1008.basecore.utils.Event
import com.kevinj1008.basecore.utils.toLiveData
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers

class CurrencyViewModel(
    private val repository: CurrencyRepository
) : BaseViewModel() {

    private val _sort = MutableLiveData<SortOrder>()
    val currencyList: LiveData<Event<List<CurrencyInfo>>> = _sort.switchMap {
        setLoading(isLoading = true)
        liveData(context = exceptionHandler + Dispatchers.IO) {
            emitSource(repository.observeCurrencyList(it).distinctUntilChanged().map { currencyList ->
                handleData(currencyList)
            })
        }
    }
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading = _isLoading.toLiveData()

    val error = _error.toLiveData()

    fun load(sortOrder: SortOrder) {
        _sort.value = sortOrder
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = Event(isLoading)
    }

    //Prevent onChange twice if observe again because of re-entering fragment, due to viewModel is
    // belong to activity
    private fun handleData(list: List<CurrencyInfo>): Event<List<CurrencyInfo>> {
        return Event(list)
    }
}