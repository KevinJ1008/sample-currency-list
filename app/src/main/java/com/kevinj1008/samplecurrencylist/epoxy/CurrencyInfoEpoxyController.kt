package com.kevinj1008.samplecurrencylist.epoxy

import androidx.lifecycle.MutableLiveData
import com.airbnb.epoxy.EpoxyController
import com.kevinj1008.basecore.utils.Event
import com.kevinj1008.basecore.utils.toLiveData
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyInfoEpoxyController : EpoxyController() {
    private val _clickEvent = MutableLiveData<Event<ClickEvent>>()
    val clickEvent = _clickEvent.toLiveData()

    private var currencyList: ArrayList<CurrencyInfo> = arrayListOf()

    override fun buildModels() {
        currencyList.forEachIndexed { index, currencyInfo ->
            currencyInfo {
                id(currencyInfo.id + index)
                name(currencyInfo.name)
                symbol(currencyInfo.symbol)
                clickListener { name, symbol ->
                    val event = ClickEvent.CurrencyEvent(position = index,
                        name = name,
                        symbol = symbol)
                    this@CurrencyInfoEpoxyController._clickEvent.value = Event(event)
                }
            }
        }
    }

    fun setCurrencyList(list: List<CurrencyInfo>?) {
        list?.apply {
            currencyList.clear()
            currencyList.addAll(this)
            requestModelBuild()
        }
    }

    /**
     * This seal class is for this recyclerView's click event hook data
     * You could extend this in your need in this recyclerView
     */
    sealed class ClickEvent {
        data class CurrencyEvent(val position: Int,
                                 val name: String,
                                 val symbol: String) : ClickEvent()
    }
}