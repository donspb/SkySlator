package ru.donspb.skyslator.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.parseSearchResults
import ru.donspb.skyslator.view.main.MainInteractor


class MainViewModel(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word) }
    }

    private suspend fun startInteractor(word: String) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

//    private fun getObserver(): DisposableObserver<AppState> {
//        return object : DisposableObserver<AppState>() {
//            override fun onNext(t: AppState) {
//                appState = t
//                liveDataForViewToObserve.value = t
//            }
//
//            override fun onError(e: Throwable) {
//                liveDataForViewToObserve.value = AppState.Error(e)
//            }
//
//            override fun onComplete() {
//            }
//        }
//    }
}