package com.ahmedvargos.agent_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AgentDetailsViewModel
@Inject constructor(
    private val useCase: AgentDetailsUseCase
) : ViewModel() {

    private val _agentsDetailsStateFlow: MutableStateFlow<Resource<AgentInfo>> =
        MutableStateFlow(Resource.Loading)

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
