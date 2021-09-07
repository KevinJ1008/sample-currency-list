package com.kevinj1008.samplecurrencylist.repository

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.datasource.LocalDataSource
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyRepositoryImpl(
    private val currencyLocalDataSource: LocalDataSource<LiveData<List<CurrencyInfo>>>
) : CurrencyRepository {

    override suspend fun observeCurrencyList(sortOrder: SortOrder): LiveData<List<CurrencyInfo>> = currencyLocalDataSource.getData(sortOrder)
//    override fun observeCurrencyListAsc(): LiveData<List<CurrencyInfo>> = currencyLocalDataSource.observeAscData()
//    override fun observeCurrencyListDesc(): LiveData<List<CurrencyInfo>> = currencyLocalDataSource.observeDescData()
}