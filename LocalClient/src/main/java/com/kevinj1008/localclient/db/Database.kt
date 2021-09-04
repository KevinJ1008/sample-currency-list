package com.kevinj1008.localclient.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevinj1008.localclient.dao.CurrencyDao
import com.kevinj1008.localclient.model.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1, exportSchema = true)
abstract class Database : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}