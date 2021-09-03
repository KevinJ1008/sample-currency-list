package com.kevinj1008.localclient.dao

import androidx.room.Dao
import androidx.room.Query
import com.kevinj1008.localclient.model.CurrencyInfo

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM `currency_list`")
    fun getCurrencyList(): List<CurrencyInfo>
}