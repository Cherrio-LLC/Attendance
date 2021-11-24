package com.cherrio.attendance.presentation.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {


    override fun useViews() {
        delay()
    }

    private fun delay(){
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 2000)
    }

    override fun getBinding(inflater: LayoutInflater): FragmentSplashBinding{
        return FragmentSplashBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        //delay()
    }
}