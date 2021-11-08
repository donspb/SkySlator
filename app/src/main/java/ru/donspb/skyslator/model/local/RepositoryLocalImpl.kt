package ru.donspb.skyslator.model.local

import ru.donspb.skyslator.model.convertDataModelSuccessToEntity
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.mapHistoryEntityToSearchResult

class RepositoryLocalImpl(private val historyDao: HistoryDao) : RepositoryLocal<List<DataModel>> {
    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }
}