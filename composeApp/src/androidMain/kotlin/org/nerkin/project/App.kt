package org.nerkin.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.nerkin.project.data.di.androidModule
import org.nerkin.project.di.sharedModule


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(sharedModule, androidModule))
        }
    }
}
