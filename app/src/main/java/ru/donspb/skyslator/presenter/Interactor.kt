package ru.donspb.skyslator.presenter

import io.reactivex.Observable

interface Interactor<T> {
    fun getData(word: String): Observable<T>
}