package ru.donspb.skyslator.view.main

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.local.RepositoryLocal
import ru.donspb.skyslator.model.local.RepositoryLocalImpl
import ru.donspb.skyslator.model.remote.Repository
import ru.donspb.skyslator.model.remote.RetrofitImplementation
import ru.donspb.skyslator.view.Interactor

class MainInteractor(
    private val repositoryLocal: RepositoryLocal<List<DataModel>>,
    private val repositoryRemote: Repository<List<DataModel>>
)
    : Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        val appState = AppState.Success(repositoryRemote.getData(word))
        repositoryLocal.saveToDB(appState)
        return appState
    }

}