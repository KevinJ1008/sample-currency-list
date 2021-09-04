package com.kevinj1008.localclient.datasource

import androidx.lifecycle.LiveData

interface LocalDataSource<T> {
    fun observeData(): LiveData<T>
    suspend fun getData(): T
//    suspend fun insert(value: T)
}