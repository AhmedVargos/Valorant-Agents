package com.ahmedvargos.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ahmedvargos.base.ui.BaseFragment
import com.ahmedvargos.favorites.databinding.FragmentFavoriteAgentsBinding


class FavoriteAgentsFragment : BaseFragment<FragmentFavoriteAgentsBinding>() {


    companion object {

        fun newInstance() = FavoriteAgentsFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteAgentsBinding
        get() = FragmentFavoriteAgentsBinding::inflate

    override fun setup() {
        //Setup views
    }
}