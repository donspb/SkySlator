package ru.donspb.skyslator.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.view.MainFragmentView

class MainPresenter<V : MainFragmentView>():
    IMainPresenter<V> {

    private var view: V? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val interactor: MainInteractor = MainInteractor()

    override fun buttonGoPressed(word: String) {
        compositeDisposable.add(
            interactor.getData(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                view?.renderData(t)
            }

            override fun onError(e: Throwable) {
                e.message?.let { AppState.Error(it) }?.let { view?.renderData(it) }
            }

            override fun onComplete() {
            }

        }
    }

    override fun attachView(view: V) {
        if (view != this.view) {
            this.view = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == this.view) {
            this.view = null
        }
    }


}