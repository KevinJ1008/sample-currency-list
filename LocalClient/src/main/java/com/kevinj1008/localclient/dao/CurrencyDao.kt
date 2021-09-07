package com.kevinj1008.localclient.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo

//TODO: Not handle massive data issue if we have huge list in future, just make sample easy and
// complete basic feature
/**
 * Room's mechanism will help us to switch to IO/Main thread operation while query if we return liveData
 */
@Dao
interface CurrencyDao {

    @Query("SELECT * FROM `currency_list`")
    fun getCurrencyList(): LiveData<List<CurrencyInfo>>

    @Query("SELECT * FROM `currency_list` ORDER BY name ASC")
    fun sortCurrencyListByAscName(): LiveData<List<CurrencyInfo>>

    @Query("SELECT * FROM `currency_list` ORDER BY name DESC")
    fun sortCurrencyListByDescName(): LiveData<List<CurrencyInfo>>
}