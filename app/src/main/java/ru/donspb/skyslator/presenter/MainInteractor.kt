package ru.donspb.skyslator.presenter

import android.util.Log
import io.reactivex.Observable
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.remote.Repository
import ru.donspb.skyslator.model.remote.RetrofitImplementation
import ru.donspb.skyslator.presenter.Interactor

class MainInteractor: Interactor<AppState>{

    override fun getData(word: String): Observable<AppState> {
        return RetrofitImplementation().getData(word).map { AppState.Success(it) }
    }

}