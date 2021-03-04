package com.ahmedvargos.agent_details.presentation

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedvargos.agent_details.R
import com.ahmedvargos.agent_details.databinding.ActivityAgentDetailsBinding
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.ui.BaseActivity
import com.ahmedvargos.favorites.presentation.FavoriteAgentsViewModel
import com.ahmedvargos.navigator.ActionKeys
import com.ahmedvargos.uicomponents.utils.GlideApp
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.showErrorDialog
import com.ahmedvargos.uicomponents.utils.visible
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgentDetailsActivity : BaseActivity<ActivityAgentDetailsBinding>() {

    private val agentDetailsViewModel: AgentDetailsViewModel by viewModels()

    private val favoriteAgentsViewModel: FavoriteAgentsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityAgentDetailsBinding
        get() = ActivityAgentDetailsBinding::inflate

    override fun setup() {
        // Init views
        // Add the fav view and actions
        attachActions()

        // Binds the view model streams
        bindViewModels(intent.extras?.getString(ActionKeys.AGENT_ID_KEY))
    }

    private fun attachActions() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFav.setOnClickListener { favBtnView ->
            handleFavAction(favBtnView)
        }
    }

    private fun bindViewModels(agentId: String?) {
        agentDetailsViewModel.agentsDetailsStateFlow.asLiveData().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.progress.visible()
                }
                is Resource.Success -> {
                    binding.progress.gone()
                    binding.viewsGroup.visible()

                    it.data?.let { agentInfo ->
                        fillAgentDetails(agentInfo)
                    }
                }
                is Resource.Failure -> {
                    binding.progress.gone()
                    this.showErrorDialog(
                        it.failureData.message ?: getString(R.string.generic_error)
                    )
                }
                is Resource.None -> {
                    binding.progress.gone()
                }
            }
        }

        agentDetailsViewModel.getAgentDetails(agentId ?: "")
    }

    private fun fillAgentDetails(agentInfo: AgentInfo) {
        with(binding) {
            configAgentImage(agentInfo.bustPortrait ?: "")
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

    private fun handleFavAction(favBtnView: View?) {
        if (agentDetailsViewModel.agentsDetailsStateFlow.value is Resource.Success<*>) {
            val agentInfoData =
                (agentDetailsViewModel.agentsDetailsStateFlow.value
                        as Resource.Success<AgentInfo>).data
            with(agentInfoData) {
                favoriteAgentsViewModel.toggleFavoriteAgent(this?.uuid ?: "0")
                this?.toggleFav()
                (favBtnView as ImageButton).setImageResource(
                    this?.isFav?.getFavoriteImage() ?: 0
                )
            }
        }
    }

    private fun configAgentImage(bustPortrait: String) {
        GlideApp.with(this)
            .asBitmap()
            .load(bustPortrait)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let { bitmap ->
                        Palette.from(bitmap).generate {
                            // Use the dominant color in image
                            val dominantColor =
                                it?.getMutedColor(
                                    ContextCompat.getColor(
                                        this@AgentDetailsActivity,
                                        R.color.white
                                    )
                                )
                                    ?: 0x000
                            binding.ivBackground.backgroundTintList =
                                ColorStateList.valueOf(dominantColor)
                        }
                    }
                    return false
                }
            }).into(binding.ivAgentPic)
    }

    private fun Boolean.getFavoriteImage() = if (this) {
        R.drawable.iv_selected_fav
    } else {
        R.drawable.ic_unselected_fav_24
    }
}
