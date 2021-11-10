package ru.donspb.skyslator.view.history

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.local.RepositoryLocal
import ru.donspb.skyslator.model.local.RepositoryLocalImpl
import ru.donspb.skyslator.view.Interactor

class HistoryInteractor(private val repositoryLocal: RepositoryLocal<List<DataModel>>)
    : Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        return AppState.Success(repositoryLocal.getData(word))
    }
}