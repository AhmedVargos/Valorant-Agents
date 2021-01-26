package com.ahmedvargos.navigator

import android.content.Context
import android.content.Intent
import com.ahmedvargos.navigator.ActionKeys.AGENT_ID_KEY

class NavigationActions {

    fun navigateToHomeScreen(context: Context) =
        internalIntent(context, "com.ahmedvargos.home.navigate").also {
            navigate(context, it)
        }

    fun navigateToAgentDetailsScreen(context: Context, agentId: String) =
        internalIntent(context, "com.ahmedvargos.agent_details.navigate")
            .apply { putExtra(AGENT_ID_KEY, agentId) }
            .also {
                navigate(context, it)
            }

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)

    private fun navigate(context: Context, intent: Intent) = context.startActivity(intent)
}
