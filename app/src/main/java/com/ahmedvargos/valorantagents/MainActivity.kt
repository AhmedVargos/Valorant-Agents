package com.ahmedvargos.valorantagents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedvargos.navigator.NavigationActions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var navigator: NavigationActions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator.navigateToHomeScreen(this)
        finish()
    }
}
