package com.kevinj1008.localclient

import android.app.Application
import androidx.room.Room
import com.kevinj1008.localclient.dao.CurrencyDao
import com.kevinj1008.localclient.db.Database
import com.kevinj1008.localclient.interfaces.AppDataBase

class DatabaseProvider(application: Application) : AppDataBase {

    private val dataBase: Database = Room
        .databaseBuilder(application, Database::class.java, DATABASE_NAME)
        .createFromAsset(DATABASE_DIR)
        .fallbackToDestructiveMigration()
        .build()

    override fun currencyDao(): CurrencyDao = dataBase.currencyDao()

    override fun clean() {
        dataBase.clearAllTables()
    }

    companion object {
        private const val DATABASE_NAME = "currency_info.db"
        private const val DATABASE_DIR = "databases/currency.db"
    }
}