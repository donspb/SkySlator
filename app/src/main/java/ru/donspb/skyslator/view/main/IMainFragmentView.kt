package ru.donspb.skyslator.view.main

import ru.donspb.skyslator.model.data.AppState

interface MainFragmentView {
    fun renderData(appState: AppState)
}