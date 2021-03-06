package ru.donspb.skyslator.model.remote

import io.reactivex.Observable

interface Repository<T> {
    fun getData(word: String): Observable<T>
}