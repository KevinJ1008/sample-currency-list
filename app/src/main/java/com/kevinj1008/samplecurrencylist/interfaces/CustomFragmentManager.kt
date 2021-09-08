package com.kevinj1008.samplecurrencylist.interfaces

interface CustomFragmentManager {
    fun onShowSortingButton(isVisible: Boolean)
    fun onDataLoading(isLoading: Boolean)
    fun onRetryLoadData()
}