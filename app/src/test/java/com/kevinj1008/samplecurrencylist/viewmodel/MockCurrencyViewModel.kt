package com.kevinj1008.samplecurrencylist.viewmodel

import com.kevinj1008.basecore.utils.toLiveData
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository

class MockCurrencyViewModel(
    private val repository: CurrencyRepository
) : CurrencyViewModel(repository) {
    val sortOrder = _sort.toLiveData()
}