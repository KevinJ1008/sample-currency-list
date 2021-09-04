package com.kevinj1008.localclient.datasource

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.dao.CurrencyDao
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyLocalDataSource(
    private val currencyDao: CurrencyDao
) : LocalDataSource<List<CurrencyInfo>> {
    override fun observeData(): LiveData<List<CurrencyInfo>> {
        return currencyDao.getCurrencyList()
    }
    override suspend fun getData(): List<CurrencyInfo> {
        TODO()
    }

//    override suspend fun insert(value: List<CurrencyInfo>) {
//        currencyDao.insertAllCurrency(value)
//    }
}