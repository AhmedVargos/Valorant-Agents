package com.ahmedvargos.agent_details.presentation

import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedvargos.agent_details.R
import com.ahmedvargos.agent_details.databinding.ActivityAgentDetailsBinding
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.ui.BaseActivity
import com.ahmedvargos.favorites.presentation.FavoriteAgentsViewModel
import com.ahmedvargos.navigator.ActionKeys
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.loadImage
import com.ahmedvargos.uicomponents.utils.showErrorDialog
import com.ahmedvargos.uicomponents.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgentDetailsActivity : BaseActivity<ActivityAgentDetailsBinding>() {

    private val agentDetailsViewModel: AgentDetailsViewModel by viewModel()
    private val favoriteAgentsViewModel: FavoriteAgentsViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityAgentDetailsBinding
        get() = ActivityAgentDetailsBinding::inflate

    override fun setup() {
        //Init views
        //Add the fav view and actions
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFav.setOnClickListener { favBtnView ->
            with(agentDetailsViewModel.agentsDetailsStateFlow.value.data) {
                favoriteAgentsViewModel.toggleFavoriteAgent(this?.uuid ?: "0")
                (favBtnView as ImageButton).setImageResource(
                    this?.isFav?.getFavoriteImage(true) ?: 0
                )
            }
        }

        //Binds the view model streams
        bindViewModels(intent.extras?.getString(ActionKeys.AGENT_ID_KEY))
    }

    private fun bindViewModels(agentId: String?) {
        agentDetailsViewModel.agentsDetailsStateFlow.asLiveData().observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progress.gone()
                }
                Resource.Status.SUCCESS -> {
                    binding.progress.gone()
                    binding.viewsGroup.visible()

                    it.data?.let { agentInfo ->
                        fillAgentDetails(agentInfo)
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progress.gone()
                    this.showErrorDialog(
                        it.messageType?.message ?: getString(R.string.generic_error)
                    )
                }
                Resource.Status.NONE -> {
                }
            }
        }

        agentDetailsViewModel.getAgentDetails(agentId ?: "")
    }

    private fun fillAgentDetails(agentInfo: AgentInfo) {
        with(binding) {
            ivAgentPic.loadImage(agentInfo.bustPortrait)
            tvAgentName.text = agentInfo.displayName
            tvRoleType.text = agentInfo.role?.displayName
            btnFav.setImageResource(agentInfo.isFav.getFavoriteImage())
            tvBiographyText.text = agentInfo.description
            with(rvSkills) {
                layoutManager = LinearLayoutManager(context)
                adapter = SkillsRecyclerAdapter().apply { addSkills(agentInfo.abilities) }
                isNestedScrollingEnabled = false
            }
        }
    }

    private fun Boolean.getFavoriteImage(isReverse: Boolean = false): Int {
        val updatedState = if (isReverse)
            this.not()
        else
            this

        return if (updatedState) {
            R.drawable.iv_selected_fav
        } else {
            R.drawable.ic_unselected_fav_24
        }
    }
}