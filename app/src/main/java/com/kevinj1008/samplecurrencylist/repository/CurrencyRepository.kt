package com.kevinj1008.samplecurrencylist.repository

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo

interface CurrencyRepository {
    suspend fun observeCurrencyList(sortOrder: SortOrder): LiveData<List<CurrencyInfo>>
}