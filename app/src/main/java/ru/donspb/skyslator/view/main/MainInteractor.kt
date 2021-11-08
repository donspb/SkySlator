package ru.donspb.skyslator.view.main

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.local.RepositoryLocal
import ru.donspb.skyslator.model.local.RepositoryLocalImpl
import ru.donspb.skyslator.model.remote.RetrofitImplementation
import ru.donspb.skyslator.view.Interactor

class MainInteractor: Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        val appState = AppState.Success(RetrofitImplementation().getData(word))
        RepositoryLocalImpl().saveToDB(appState)
        return appState
    }

}