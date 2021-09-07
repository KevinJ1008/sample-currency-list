package com.kevinj1008.localclient.datasource

import androidx.lifecycle.LiveData

interface LocalDataSource<T> {
//    fun observeData(): LiveData<T>
//    fun observeAscData(): LiveData<T>
//    fun observeDescData(): LiveData<T>
    suspend fun getData(vararg args: Any): T
}