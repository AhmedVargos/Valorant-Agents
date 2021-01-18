package com.ahmedvargos.navigator

import android.content.Context
import android.content.Intent

class NavigationActions {

    fun navigateToHomeScreen(context: Context) =
        internalIntent(context, "com.ahmedvargos.home.navigate").also {
            navigate(context, it)
        }

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)

    private fun navigate(context: Context, intent: Intent) = context.startActivity(intent)
}