package com.attendance.student.presentation.attendances

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.attendance.student.R
import com.attendance.student.base.BaseFragment
import com.attendance.student.data.firebase.ClassDetails
import com.attendance.student.databinding.DialogMarkAttendanceBinding
import com.attendance.student.databinding.FragmentAttendancesBinding
import com.attendance.student.databinding.FragmentSignupBinding
import com.attendance.student.utils.collectInView
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class CreateAttendance : BaseFragment<DialogMarkAttendanceBinding>() {

    private val viewModel by viewModels<AttViewModel>()
    private lateinit var mClassDetails: ClassDetails
    private var success = false

    override fun getBinding(layoutInflater: LayoutInflater): DialogMarkAttendanceBinding {
        return DialogMarkAttendanceBinding.inflate(layoutInflater)
    }

    override fun useViews() {
        binding.apply {
            btnCreate.setOnClickListener {
                if (success){
                    addClass()
                }else {
                    if (matTitle.text!!.length < 5) {
                        Toast.makeText(requireContext(), "Enter class Id", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.getClass(matTitle.text.toString())
                    }
                }
            }
        }
        viewModel.state.collectInView(viewLifecycleOwner){
            println("State: $it")
            with(it){
                loading?.let {
                    binding.apply {
                        loading.isVisible = it
                        layClassDetails.isVisible = false
                    }
                }
                classDetails?.let { classDetails ->
                    mClassDetails = classDetails
                    success = true
                    binding.apply {
                        noClass.isVisible = false
                        matClassId.text = classDetails.classId.toString()
                        matClassTitle.text = classDetails.className
                        layClassDetails.isVisible = true
                        btnCreate.text = "Proceed to scan"
                    }
                }
                error?.let {
                    binding.noClass.isVisible = true
                    binding.layClassDetails.isVisible = false
                }
            }
        }
    }

    private fun addClass() {
        //Goto scan fragment
        val bundle = Bundle().apply {
            this.putInt("Class", mClassDetails.classId)
        }
        findNavController().navigate(R.id.action_drawer_home_to_add_attendance_nav, bundle)

    }

}