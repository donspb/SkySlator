package ru.donspb.skyslator

import android.app.Application
import org.koin.core.context.startKoin
import ru.donspb.skyslator.di.application
import ru.donspb.skyslator.di.historyScreen
import ru.donspb.skyslator.di.mainScreen

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(application, mainScreen, historyScreen)) }
    }
}