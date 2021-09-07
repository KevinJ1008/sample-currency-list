package com.kevinj1008.localclient.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import com.kevinj1008.localclient.dao.CurrencyDao
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyLocalDataSource(
    private val currencyDao: CurrencyDao
) : LocalDataSource<LiveData<List<CurrencyInfo>>> {

    override suspend fun getData(vararg args: Any): LiveData<List<CurrencyInfo>> = liveData {
        //TODO: maybe should emit throwable??
        if (args.isEmpty() || args[0] !is SortOrder) return@liveData emit(emptyList())
        val liveData = when (args[0] as SortOrder) {
            SortOrder.ASCENDING -> currencyDao.sortCurrencyListByAscName()
            SortOrder.DESCENDING -> currencyDao.sortCurrencyListByDescName()
            else -> currencyDao.getCurrencyList()
        }
        emitSource(liveData)
    }
}