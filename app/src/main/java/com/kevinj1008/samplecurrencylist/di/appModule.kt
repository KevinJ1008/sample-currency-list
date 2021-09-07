package com.kevinj1008.samplecurrencylist.di

import androidx.lifecycle.LiveData
import com.kevinj1008.localclient.DatabaseProvider
import com.kevinj1008.localclient.datasource.CurrencyLocalDataSource
import com.kevinj1008.localclient.datasource.LocalDataSource
import com.kevinj1008.localclient.interfaces.AppDataBase
import com.kevinj1008.localclient.model.CurrencyInfo
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepositoryImpl
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sourceModule = module {
    single<AppDataBase> { DatabaseProvider(androidApplication()) }
}

val dataSourceModule = module {
    factory<LocalDataSource<LiveData<List<CurrencyInfo>>>> { CurrencyLocalDataSource(get<AppDataBase>().currencyDao()) }
}

val repositoryModule = module {
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { CurrencyViewModel(get()) }
}