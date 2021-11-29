package com.attendance.student.presentation.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.attendance.student.R
import com.attendance.student.base.BaseFragment
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.databinding.FragmentSplashBinding
import com.attendance.student.utils.collectInView

import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private var isLoop = false
    private val viewModel by viewModels<SplashViewModel>()
    private var notSignedIn = false
    override fun useViews() {
        viewModel.state.collectInView(viewLifecycleOwner){
            with(it) {
                studentEntity?.let { state ->
                    notSignedIn = state.id == 0
                    checkAuthState(state)
                }
            }
        }
    }

    private fun delay(action: () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed({
            if (isVisible) {
                action()
            }
        }, 2000)
    }
    private fun checkAuthState(studentEntity: StudentEntity){
        if (studentEntity.id == 0){
            delay {
                gotoSignup()
            }
        }else {
            delay {
                gotoHome()
            }
        }
    }
    private fun gotoSignup(){
        findNavController().navigate(R.id.action_splashFragment_to_signupFragment)
    }
    private fun gotoHome(){
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    override fun getBinding(layoutInflater: LayoutInflater): FragmentSplashBinding{
        return FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        when{
            isLoop && notSignedIn -> {
                findNavController().navigate(R.id.action_splashFragment_to_signupFragment)
            }
            isLoop && !notSignedIn ->{
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isLoop = true
    }
}