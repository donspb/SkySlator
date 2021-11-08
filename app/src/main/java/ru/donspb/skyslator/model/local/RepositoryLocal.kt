package ru.donspb.skyslator.model.local

import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.remote.Repository

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}