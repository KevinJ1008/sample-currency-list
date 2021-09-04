package com.kevinj1008.localclient.interfaces

import com.kevinj1008.localclient.dao.CurrencyDao

interface AppDataBase {
    fun currencyDao(): CurrencyDao
    fun clean()
}