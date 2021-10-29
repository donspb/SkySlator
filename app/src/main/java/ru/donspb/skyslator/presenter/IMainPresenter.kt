package ru.donspb.skyslator.presenter

import ru.donspb.skyslator.view.MainFragmentView

interface IMainPresenter<V : MainFragmentView> {
    fun buttonGoPressed(word: String)
    fun attachView(view: V)
    fun detachView(view: V)
}