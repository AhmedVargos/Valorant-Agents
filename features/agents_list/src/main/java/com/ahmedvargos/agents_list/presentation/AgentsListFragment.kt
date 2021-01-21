package com.ahmedvargos.agents_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedvargos.agents_list.databinding.FragmentAgentsListBinding
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.ui.BaseFragment
import com.ahmedvargos.uicomponents.adapters.AgentsRecyclerAdapter
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.visible
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgentsListFragment : BaseFragment<FragmentAgentsListBinding>() {

    companion object {
        fun newInstance() = AgentsListFragment()
    }

    private val agentCellViewModel: AgentCellViewModel by viewModel()
    private val agentsListViewModel: AgentsListViewModel by viewModel()

    private val agentsAdapter by lazy {
        AgentsRecyclerAdapter(agentCellActionsDelegate = agentCellViewModel)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAgentsListBinding
        get() = FragmentAgentsListBinding::inflate

    override fun setup() {
        //Setup views
        with(binding.rvAgentsList) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = agentsAdapter
            isNestedScrollingEnabled = false
        }

        bindViewModels()
    }

    private fun bindViewModels() {
        agentsListViewModel.agentsStateFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    //Show loading & hide rv
                    it.data?.let { agents ->
                        fillAgentsWithData(agents)
                    } ?: run {
                        binding.progressView.visible()
                        binding.rvAgentsList.gone()
                    }
                }
                Resource.Status.SUCCESS -> {
                    fillAgentsWithData(it.data)
                }
                Resource.Status.ERROR -> {
                    //Show error toast & hide progress
                    binding.progressView.gone()
                    Toast.makeText(requireContext(), it.messageType?.message, Toast.LENGTH_SHORT)
                        .show()
                }
                Resource.Status.NONE -> {

                }
            }
        }

        agentsListViewModel.getPopularAgents()
    }

    private fun fillAgentsWithData(agents: List<AgentInfo>?) {
        binding.progressView.gone()
        binding.rvAgentsList.visible()
        agentsAdapter.addAgents(agents ?: mutableListOf())
    }
}