package com.ahmedvargos.agents_list.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CacheStateSharedViewModel : ViewModel() {
    private val _cachedStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val cachedStateFlow: StateFlow<Boolean> = _cachedStateFlow

    fun updateCachedDataState(newState: Boolean) {
        _cachedStateFlow.value = newState
    }

}