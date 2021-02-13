package com.ahmedvargos.agents_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedvargos.agents_list.R
import com.ahmedvargos.agents_list.databinding.FragmentAgentsListBinding
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.ui.BaseFragment
import com.ahmedvargos.navigator.NavigationActions
import com.ahmedvargos.uicomponents.adapters.AgentsRecyclerAdapter
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.showErrorDialog
import com.ahmedvargos.uicomponents.utils.visible
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgentsListFragment : BaseFragment<FragmentAgentsListBinding>() {

    companion object {
        fun newInstance() = AgentsListFragment()
    }

    private val agentCellViewModel: AgentCellViewModel by viewModel()
    private val agentsListViewModel: AgentsListViewModel by viewModel()
    private val cacheStateViewModel by lazy {
        requireActivity().getViewModel<CacheStateSharedViewModel>()
    }

    private val navigator: NavigationActions by inject()

    private val agentsAdapter by lazy {
        AgentsRecyclerAdapter(agentCellActionsDelegate = agentCellViewModel)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAgentsListBinding
        get() = FragmentAgentsListBinding::inflate

    override fun setup() {
        // Setup views
        with(binding.rvAgentsList) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = agentsAdapter
        }

        bindViewModels()
    }

    private fun bindViewModels() {
        agentsListViewModel.agentsStateFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        // Show loading & hide rv
                        binding.progressView.visible()
                        binding.rvAgentsList.gone()
                    }
                    is Resource.Success -> {
                        fillAgentsWithData(it.data)
                        cacheStateViewModel.updateCachedDataState(
                            it.source != DataSource.REMOTE
                        )
                    }
                    is Resource.Failure -> {
                        // Show error toast & hide progress
                        binding.progressView.gone()
                        requireContext().showErrorDialog(
                            it.failureData.message ?: getString(R.string.generic_error)
                        )
                    }
                    is Resource.None -> {
                    }
                }
            }

        agentCellViewModel.openAgentDetails.observe(this) {
            navigator.navigateToAgentDetailsScreen(requireContext(), agentId = it.uuid)
        }
    }

    private fun fillAgentsWithData(agents: List<AgentInfo>?) {
        binding.progressView.gone()
        binding.rvAgentsList.visible()
        agentsAdapter.addAgents(agents ?: mutableListOf())
    }
}
