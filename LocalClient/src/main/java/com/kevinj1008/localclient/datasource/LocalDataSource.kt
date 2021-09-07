package com.kevinj1008.localclient.datasource

import androidx.lifecycle.LiveData

interface LocalDataSource<T> {
    suspend fun getData(vararg args: Any): T
}