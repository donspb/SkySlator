package ru.donspb.skyslator

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.donspb.skyslator.model.DataModel

class Contract {

    @AddToEndSingle
    interface MainView : MvpView

    interface MainFragmentView: MvpView {
        fun showLoading()
        fun showError(error: String)
        fun showData(data: List<DataModel>)
    }

    interface MainPresenter {
        fun buttonGoPressed(word: String)
    }

    interface MainModel {
        fun getData(): List<Any>
    }
}