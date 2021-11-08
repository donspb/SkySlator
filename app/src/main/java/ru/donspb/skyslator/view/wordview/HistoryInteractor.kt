package ru.donspb.skyslator.view.wordview

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.local.RepositoryLocalImpl
import ru.donspb.skyslator.view.Interactor

class HistoryInteractor : Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        return AppState.Success(RepositoryLocalImpl().getData(word))
    }
}