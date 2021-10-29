package ru.donspb.skyslator.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.presenter.MainInteractor

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor()
) : BaseViewModel<AppState>() {
    private var appState: AppState? = null

    override fun getData(word: String): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
        return super.getData(word)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                appState = t
                liveDataForViewToObserve.value = t
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}