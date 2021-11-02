package ru.donspb.skyslator.model.remote

interface Repository<T> {
    suspend fun getData(word: String): T
}