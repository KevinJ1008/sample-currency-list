package com.kevinj1008.samplecurrencylist.repository

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.model.CurrencyInfo

interface CurrencyRepository {
    fun observeCurrencyList(): LiveData<List<CurrencyInfo>>
//    suspend fun insert(list: List<CurrencyInfo>)
}