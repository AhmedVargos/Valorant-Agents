package com.ahmedvargos.home

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.agents_list.presentation.CacheStateSharedViewModel
import com.ahmedvargos.base.ui.BaseActivity
import com.ahmedvargos.favorites.presentation.FavoriteAgentsFragment
import com.ahmedvargos.home.databinding.ActivityHomeBinding
import com.ahmedvargos.uicomponents.utils.gone
import com.ahmedvargos.uicomponents.utils.visible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    @Inject
    lateinit var agentsListFragment: AgentsListFragment

    @Inject
    lateinit var favoriteListFragment: FavoriteAgentsFragment

    private val cacheStateViewModel: CacheStateSharedViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setup() {
        // Bind View model
        bindViewModels()
        // Setup Ui views
        binding.vpHome.adapter =
            HomeViewPagerAdapter(this, listOf(agentsListFragment, favoriteListFragment))

        // attaching tab mediator
        TabLayoutMediator(
            binding.tabLayout, binding.vpHome
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = when (position) {
                0 -> getString(R.string.popular_agents)
                else -> getString(R.string.favorite_agents)
            }
        }.attach()
    }

    private fun bindViewModels() {
        cacheStateViewModel.cachedStateFlow.asLiveData().observe(this, { cacheViewState ->
            if (cacheViewState)
                binding.tvCachedDataDisplayed.visible()
            else
                binding.tvCachedDataDisplayed.gone()
        })
    }
}
