package com.ahmedvargos.favorites.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteAgentsFragment : BaseFragment<FragmentFavoriteAgentsBinding>() {

    companion object {
        fun newInstance() = FavoriteAgentsFragment()
    }

    private val agentCellViewModel: AgentCellViewModel by viewModels()

    private val favoriteAgentsViewModel: FavoriteAgentsViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationActions

    private val agentsAdapter by lazy {
        AgentsRecyclerAdapter(agentCellActionsDelegate = agentCellViewModel)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentFavoriteAgentsBinding
        get() = FragmentFavoriteAgentsBinding::inflate

    override fun setup() {
        // Setup views
        with(binding.rvAgents) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = agentsAdapter
        }

        // Bind view model
        bindViewModels()
    }

    private fun bindViewModels() {
        favoriteAgentsViewModel.agentsStateFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    with(binding) {
                        progress.visible()
                        tvNoFavs.gone()
                        rvAgents.gone()
                    }
                }
                is Resource.Success -> {
                    binding.progress.gone()
                    it.data
                        ?.takeIf { list -> list.isNotEmpty() }
                        ?.let { agentsList ->
                            binding.tvNoFavs.gone()
                            fillViewWithData(agentsList)
                        } ?: kotlin.run {
                        binding.rvAgents.gone()
                        binding.tvNoFavs.visible()
                    }
                }
                is Resource.Failure -> {
                    // Show error toast & hide progress
                    binding.progress.gone()
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

    private fun fillViewWithData(agents: List<AgentInfo>) {
        binding.rvAgents.visible()
        agentsAdapter.addAgents(agents)
    }
}
