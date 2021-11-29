package com.attendance.student.presentation.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.attendance.student.R
import com.attendance.student.base.BaseFragment
import com.attendance.student.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {

    private val viewModel by viewModels<SignupViewModel>()

    override fun getBinding(layoutInflater: LayoutInflater): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(layoutInflater)
    }

    override fun useViews() {
        binding.apply {
            btnCreate.setOnClickListener {
                when{
                    matTitle.text!!.isEmpty() ->{
                        tilFullname.error = "Enter your full name"
                    }
                    matMatric.text!!.isEmpty() ->{
                        tilMatric.error = "Enter your matric no"
                    }
                    matDept.text!!.isEmpty() ->{
                        tilDept.error = "Enter your department"
                    }
                    else ->{
                        val fullname = matTitle.text.toString()
                        val matric = matMatric.text.toString()
                        val dept = matDept.text.toString()
                       viewModel.signup(fullname, matric, dept)
                        findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

    }


}