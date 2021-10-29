package ru.donspb.skyslator.view

import ru.donspb.skyslator.model.data.AppState

interface MainFragmentView {
    fun renderData(appState: AppState)
}