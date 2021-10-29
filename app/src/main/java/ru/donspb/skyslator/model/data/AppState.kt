package ru.donspb.skyslator.model.data

sealed class AppState {
    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}