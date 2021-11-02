package ru.donspb.skyslator.view

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.remote.RetrofitImplementation

class MainInteractor: Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        return AppState.Success(RetrofitImplementation().getData(word))
    }

}