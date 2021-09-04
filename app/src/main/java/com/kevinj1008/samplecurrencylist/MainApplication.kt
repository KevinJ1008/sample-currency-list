package com.kevinj1008.samplecurrencylist

import android.app.Application
import com.kevinj1008.samplecurrencylist.di.dataSourceModule
import com.kevinj1008.samplecurrencylist.di.repositoryModule
import com.kevinj1008.samplecurrencylist.di.sourceModule
import com.kevinj1008.samplecurrencylist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(sourceModule, dataSourceModule, repositoryModule, viewModelModule))
        }
    }
}