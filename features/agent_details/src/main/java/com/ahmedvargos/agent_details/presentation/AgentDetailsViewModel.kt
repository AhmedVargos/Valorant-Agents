package com.ahmedvargos.agent_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AgentDetailsViewModel(private val useCase: AgentDetailsUseCase) : ViewModel() {

    private val _agentsDetailsStateFlow: MutableStateFlow<Resource<AgentInfo>> =
        MutableStateFlow(Resource.none())

    val agentsDetailsStateFlow: StateFlow<Resource<AgentInfo>> = _agentsDetailsStateFlow

    fun getAgentDetails(agentId: String) {
        viewModelScope.launch {
            useCase.invoke(agentId)
                .collect {
                    _agentsDetailsStateFlow.value = it
                }
        }
    }
}
