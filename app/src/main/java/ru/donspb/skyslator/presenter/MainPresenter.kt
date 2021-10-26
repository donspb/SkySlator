package ru.donspb.skyslator.presenter

import android.view.View
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.donspb.skyslator.Contract
import ru.donspb.skyslator.model.remote.RetrofitImplementation

class MainPresenter(val view: Contract.MainFragmentView): MvpPresenter<Contract.MainView>(), Contract.MainPresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val retroImpl: RetrofitImplementation = RetrofitImplementation()

    override fun buttonGoPressed(word: String) {
        view.showLoading()
            retroImpl.getData(word)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({
                           view.showData(it)
                },
                    {// TODO: OnError
                })
    }



}