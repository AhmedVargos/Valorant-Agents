package com.ahmedvargos.valorantagents

import android.app.Application
import com.ahmedvargos.local.di.getLocalModule
import com.ahmedvargos.navigator.di.getNavigatorModule
import com.ahmedvargos.remote.di.getRemoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val list = listOf(
            getRemoteModule(BuildConfig.BASE_URL, BuildConfig.DEBUG),
            getLocalModule(),
            getNavigatorModule()
        )

        startKoin {
            modules(list)
            androidContext(this@App)
        }
    }
}