package com.kevinj1008.samplecurrencylist.epoxy

import androidx.lifecycle.MutableLiveData
import com.airbnb.epoxy.EpoxyController
import com.kevinj1008.basecore.utils.Event
import com.kevinj1008.basecore.utils.toLiveData
import com.kevinj1008.localclient.model.CurrencyInfo

class CurrencyInfoEpoxyController : EpoxyController() {
    private val _clickEvent = MutableLiveData<Event<Void?>>()
    val clickEvent = _clickEvent.toLiveData()

    private var currencyList: ArrayList<CurrencyInfo> = arrayListOf()

    override fun buildModels() {
        currencyList.forEachIndexed { index, currencyInfo ->
            currencyInfo {
                id(currencyInfo.id + index)
                name(currencyInfo.name)
                symbol(currencyInfo.symbol)
                clickListener {
                    this@CurrencyInfoEpoxyController._clickEvent.value = Event(null)
                }
            }
        }
    }

    fun setCurrencyList(list: List<CurrencyInfo>?) {
        list?.apply {
            currencyList.addAll(this)
            requestModelBuild()
        }
    }
}