package me.pegbeer.filmku

import android.app.Application
import dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class FilmKuApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        GlobalContext.startKoin{
            androidLogger()
            androidContext(this@FilmKuApplication)
            modules(dataModule)
        }
    }
}