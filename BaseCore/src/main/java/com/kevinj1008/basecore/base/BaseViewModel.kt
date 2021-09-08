package com.kevinj1008.basecore.base

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevinj1008.basecore.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    protected val _error = MutableLiveData<Event<Throwable>>()
    protected val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        _error.setValueWithSync(Event(throwable))
    }

    protected fun <T : Any> MutableLiveData<T>.setValueWithSync(value: T?) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            this.value = value
        } else {
            this.postValue(value)
        }
    }
}