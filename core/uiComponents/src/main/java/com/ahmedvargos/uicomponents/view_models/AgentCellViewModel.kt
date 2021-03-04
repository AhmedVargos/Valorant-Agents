package com.ahmedvargos.uicomponents.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.uicomponents.custom_views.AgentCellView
import com.ahmedvargos.uicomponents.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgentCellViewModel @Inject constructor() : ViewModel(),
    AgentCellView.AgentCellActionsDelegate {

    private val _openAgentDetails: MutableLiveData<AgentInfo> = SingleLiveData()
    val openAgentDetails: LiveData<AgentInfo> = _openAgentDetails

    override fun onCellClicked(agent: AgentInfo) {
        _openAgentDetails.postValue(agent)
    }
}
