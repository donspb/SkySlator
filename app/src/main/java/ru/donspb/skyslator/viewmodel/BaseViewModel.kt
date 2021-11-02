package ru.donspb.skyslator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.donspb.skyslator.model.data.AppState

abstract class BaseViewModel<T : AppState>(
    protected val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel()
{

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData(word: String)

    abstract fun handleError(error: Throwable)
}