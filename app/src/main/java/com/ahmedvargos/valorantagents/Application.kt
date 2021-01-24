package com.ahmedvargos.valorantagents

import android.app.Application
import com.ahmedvargos.agent_details.di.getAgentDetailsModule
import com.ahmedvargos.agents_list.di.getAgentsListModule
import com.ahmedvargos.home.di.getHomeModules
import com.ahmedvargos.local.di.getLocalModule
import com.ahmedvargos.navigator.di.getNavigatorModule
import com.ahmedvargos.remote.di.getRemoteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        val list = listOf(
            getRemoteModule(BuildConfig.BASE_URL, BuildConfig.DEBUG),
            getLocalModule(),
            getNavigatorModule(),
            getHomeModules(),
            getAgentsListModule(),
            getAgentDetailsModule()
        )

        startKoin {
            modules(list)
            androidContext(this@App)
        }
    }
}