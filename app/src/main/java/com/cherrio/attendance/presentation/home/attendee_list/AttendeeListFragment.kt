package com.cherrio.attendance.presentation.home.attendee_list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.databinding.DialogAddAttendanceBinding
import com.cherrio.attendance.databinding.FragmentAttendanceListBinding
import com.cherrio.attendance.databinding.FragmentAttendeeListBinding
import com.cherrio.attendance.presentation.home.HomeFragment
import com.cherrio.attendance.utils.collectInView
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class AttendeeListFragment : BaseFragment<FragmentAttendeeListBinding>() {

    private val viewModel by viewModels<AttendeeListViewModel>()
    private val attendeeAdapter = AttendeeAdapter()

    override fun useViews() {
        handleBackPressed()
        val id = requireArguments().getString("Class")!!
        viewModel.getAttendeeList(id)
        binding.apply {
            attendeeList.adapter = attendeeAdapter

        }
        attendeeAdapter.addItemClicked { entity ->
            viewModel.deleteAttendee(entity)
        }
        viewModel.state.collectInView(viewLifecycleOwner) {
            renderUi(it)
        }

    }

    private fun renderUi(state: AttendeeState) {
        with(state) {
            list?.let {
                attendeeAdapter.submitList(it)
            }
        }
    }
    private fun handleBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goBack()
                }
            })
    }
    private fun goBack(){
        findNavController().navigateUp()
    }


    override fun getBinding(layoutInflater: LayoutInflater): FragmentAttendeeListBinding {
        return FragmentAttendeeListBinding.inflate(layoutInflater)
    }

}