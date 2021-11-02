package ru.donspb.skyslator.di

import org.koin.dsl.module
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.model.remote.Repository
import ru.donspb.skyslator.model.remote.RetrofitImplementation
import ru.donspb.skyslator.view.MainInteractor
import ru.donspb.skyslator.viewmodel.MainViewModel

val application = module {
    single<Repository<List<DataModel>>>() {
        RetrofitImplementation()
    }
}

val mainScreen = module {
    factory { MainInteractor() }
    factory { MainViewModel(get())}
}