package com.ahmedvargos.valorantagents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmedvargos.navigator.NavigationActions
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val navigator: NavigationActions by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator.navigateToHomeScreen(this)
        finish()
    }
}