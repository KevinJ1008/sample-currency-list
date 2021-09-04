package com.kevinj1008.samplecurrencylist.repository

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.datasource.LocalDataSource
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyRepositoryImpl(
    private val currencyLocalDataSource: LocalDataSource<List<CurrencyInfo>>
) : CurrencyRepository {

    override fun observeCurrencyList(): LiveData<List<CurrencyInfo>> = currencyLocalDataSource.observeData()

//    override suspend fun insert(list: List<CurrencyInfo>) = withContext(Dispatchers.IO) {
//
//    }
}