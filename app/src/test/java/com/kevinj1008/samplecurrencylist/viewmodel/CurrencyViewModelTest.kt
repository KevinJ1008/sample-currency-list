package com.kevinj1008.samplecurrencylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kevinj1008.basecore.utils.Event
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.localclient.model.CurrencyInfo
import com.kevinj1008.samplecurrencylist.repository.CurrencyRepository
import com.kevinj1008.testcore.BaseTest
import com.kevinj1008.testcore.utils.getOrAwaitValue
import com.kevinj1008.testcore.utils.observeForTesting
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrencyViewModelTest : BaseTest() {
    private val repository = mockk<CurrencyRepository>()
    private val viewModel = MockCurrencyViewModel(repository)

    @Test
    fun load_passInSortOrder_loadBaseOnSortOrder() {
        //Arrange
        mockkObject(SortOrder::class)
        val sortOrder = SortOrder.ORIGIN

        //Act
        viewModel.load(sortOrder)

        //Assert
        viewModel.sortOrder.observeForTesting {
            assertEquals(it.value, sortOrder)
        }
    }

    @Test
    fun setLoading_passInBoolean_triggerByFlag() {
        //Arrange
        val isLoading = mockk<Boolean>(relaxed = true)

        //Act
        viewModel.setLoading(isLoading)

        //Assert
        viewModel.isLoading.observeForTesting {
            assertEquals(it.value?.peekContent(), isLoading)
        }
    }

    //TODO: refactor another way to test more correctly and concisely
    @Test
    fun load_loadBySortOrder_returnCurrencyListLiveData() {
        //Arrange
        mockkObject(SortOrder::class)
        val sortOrder = SortOrder.ASCENDING
        val currencyList = mockk<List<CurrencyInfo>>(relaxed = true)
        val observer = mockk<Observer<Event<List<CurrencyInfo>>>>(relaxed = true)
        val observer2 = mockk<Observer<List<CurrencyInfo>>>(relaxed = true)
        val liveData = mockk<LiveData<List<CurrencyInfo>>>(relaxed = true)
        every { liveData.value } returns currencyList
        coEvery { repository.observeCurrencyList(sortOrder) } returns liveData
        coEvery { repository.observeCurrencyList(sortOrder).observeForever(observer2) } just Runs

        //Act
        //we need to observeForever at begin to make switchMap works
        viewModel.currencyList.observeForever(observer)
        viewModel.load(sortOrder)
        observer2.onChanged(currencyList)

        //Assert
        assertEquals(liveData.value, currencyList)
        coVerify {
            repository.observeCurrencyList(sortOrder)
        }
    }
}