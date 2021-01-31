package com.ahmedvargos.agents_list.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedvargos.agents_list.domain.usecase.AgentsListUseCase
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AgentsListViewModel(private val useCase: AgentsListUseCase) : ViewModel() {

    private val _agentsStateFlow: MutableStateFlow<Resource<List<AgentInfo>>> =
        MutableStateFlow(Resource.loading())

    val agentsStateFlow: StateFlow<Resource<List<AgentInfo>>> = _agentsStateFlow

    init {
        getPopularAgents()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getPopularAgents() {
        viewModelScope.launch {
            useCase.invoke()
                .collect {
                    _agentsStateFlow.value = it
                }
        }
    }
}
