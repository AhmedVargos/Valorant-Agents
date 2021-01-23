package com.ahmedvargos.agent_details

import android.view.LayoutInflater
import com.ahmedvargos.agent_details.databinding.ActivityAgentDetailsBinding
import com.ahmedvargos.base.ui.BaseActivity

class AgentDetailsActivity : BaseActivity<ActivityAgentDetailsBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityAgentDetailsBinding
        get() = ActivityAgentDetailsBinding::inflate

    override fun setup() {
        //Init views
        //TODO get agent ID from intent and query DB for agent
        //TODO populate all views with data
        //TODO add the fav view and actions
    }
}