package com.ahmedvargos.favorites.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.ui.BaseFragment
import com.ahmedvargos.favorites.R
import com.ahmedvargos.favorites.databinding.FragmentFavoriteAgentsBinding
import com.ahmedvargos.navigator.NavigationActions
import com.ahmedvargos.uicomponents.adapters.AgentsRecyclerAdapter
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.showErrorDialog
import com.ahmedvargos.uicomponents.utils.visible
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteAgentsFragment : BaseFragment<FragmentFavoriteAgentsBinding>() {

    companion object {
        fun newInstance() = FavoriteAgentsFragment()
    }

    private val agentCellViewModel: AgentCellViewModel by viewModel()
    private val favoriteAgentsViewModel: FavoriteAgentsViewModel by viewModel()
    private val navigator: NavigationActions by inject()

    private val agentsAdapter by lazy {
        AgentsRecyclerAdapter(agentCellActionsDelegate = agentCellViewModel)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteAgentsBinding
        get() = FragmentFavoriteAgentsBinding::inflate

    override fun setup() {
        //Setup views
        with(binding.rvAgents) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = agentsAdapter
        }

        //Bind view model
        bindViewModels()
    }

    private fun bindViewModels() {
        favoriteAgentsViewModel.agentsStateFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    with(binding) {
                        progress.visible()
                        tvNoFavs.gone()
                        rvAgents.gone()
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding.progress.gone()
                    it.data?.takeIf { it.isNotEmpty() }?.let { agentsList ->
                        binding.tvNoFavs.gone()
                        fillViewWithData(agentsList)
                    } ?: kotlin.run {
                        binding.rvAgents.gone()
                        binding.tvNoFavs.visible()
                    }

                }
                Resource.Status.ERROR -> {
                    //Show error toast & hide progress
                    binding.progress.gone()
                    requireContext().showErrorDialog(
                        it.messageType?.message ?: getString(R.string.generic_error)
                    )
                }
                Resource.Status.NONE -> {

                }
            }
        }

        agentCellViewModel.openAgentDetails.observe(this) {
            navigator.navigateToAgentDetailsScreen(requireContext(), agentId = it.uuid)
        }

        favoriteAgentsViewModel.getFavoriteAgents()
    }

    private fun fillViewWithData(agents: List<AgentInfo>) {
        binding.rvAgents.visible()
        agentsAdapter.addAgents(agents)
    }
}