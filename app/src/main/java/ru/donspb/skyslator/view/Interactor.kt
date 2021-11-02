package ru.donspb.skyslator.view

interface Interactor<T> {
    suspend fun getData(word: String): T
}