package com.kevinj1008.localclient.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kevinj1008.localclient.model.CurrencyInfo

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM `currency_list`")
    fun getCurrencyList(): LiveData<List<CurrencyInfo>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllCurrency(list: List<CurrencyInfo>)
}