package com.ahmedvargos.home

import android.view.LayoutInflater
import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.base.ui.BaseActivity
import com.ahmedvargos.favorites.FavoriteAgentsFragment
import com.ahmedvargos.home.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject


class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val agentsListFragment: AgentsListFragment by inject()
    private val favoriteListFragment: FavoriteAgentsFragment by inject()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setup() {
        //Setup Ui views
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
}