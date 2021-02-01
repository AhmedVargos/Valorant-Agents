package com.ahmedvargos.base.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(emptyList())
        }
    }
}
