package com.ahmedvargos.favorites.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsInquiryUseCase
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsToggleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteAgentsViewModel(
    private val inquiryUseCase: FavoriteAgentsInquiryUseCase,
    private val toggleUseCase: FavoriteAgentsToggleUseCase
) : ViewModel() {
    private val _agentsStateFlow: MutableStateFlow<Resource<List<AgentInfo>>> =
        MutableStateFlow(Resource.loading())

    val agentsStateFlow: StateFlow<Resource<List<AgentInfo>>> = _agentsStateFlow

    private val _favAgentToggleStateFlow: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.loading())

    val favAgentToggleStateFlow: StateFlow<Resource<Boolean>> = _favAgentToggleStateFlow

    init {
        getFavoriteAgents()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getFavoriteAgents() {
        viewModelScope.launch {
            inquiryUseCase()
                .collect {
                    _agentsStateFlow.value = it
                }
        }
    }

    fun toggleFavoriteAgent(agentId: String) {
        viewModelScope.launch {
            toggleUseCase(agentId)
                .collect {
                    _favAgentToggleStateFlow.value = it
                }
        }
    }
}
