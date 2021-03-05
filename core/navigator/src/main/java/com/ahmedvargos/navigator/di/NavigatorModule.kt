package com.ahmedvargos.navigator.di

import com.ahmedvargos.navigator.NavigationActions
import org.koin.dsl.module

fun getNavigatorModule() = module {
    single { NavigationActions() }
}
