package com.attendance.lecturer.presentation.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.attendance.lecturer.R
import com.attendance.lecturer.base.BaseFragment
import com.attendance.lecturer.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private var isLoop = false

    override fun useViews() {
        delay()
    }

    private fun delay(){
        Handler(Looper.getMainLooper()).postDelayed({
            if (isVisible) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 2000)
    }

    override fun getBinding(layoutInflater: LayoutInflater): FragmentSplashBinding{
        return FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        if (isLoop){
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        isLoop = true
    }
}