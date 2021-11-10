package ru.donspb.skyslator.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.parseLocalSearchResult
import ru.donspb.skyslator.view.history.HistoryInteractor

class HistoryViewModel(private val interactor: HistoryInteractor) : BaseViewModel<AppState>() {

    private val liveDattaForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDattaForViewToObserve
    }

    override fun getData(word: String) {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word) }
    }

    private suspend fun startInteractor(word: String) {
        _mutableLiveData.postValue(parseLocalSearchResult(interactor.getData(word)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

}