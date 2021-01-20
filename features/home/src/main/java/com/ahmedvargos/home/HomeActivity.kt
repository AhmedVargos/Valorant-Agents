package com.ahmedvargos.home

import android.view.LayoutInflater
import com.ahmedvargos.base.ui.BaseActivity
import com.ahmedvargos.home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setup() {
        //Setup Ui views

    }
}