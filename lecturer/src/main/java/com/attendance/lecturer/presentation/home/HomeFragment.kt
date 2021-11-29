package com.attendance.lecturer.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.attendance.lecturer.R
import com.attendance.lecturer.base.BaseFragment
import com.attendance.lecturer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun useViews() {
        binding.apply {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        requireActivity().finish()
                    }
                })
            toolbar.setNavigationOnClickListener{
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(GravityCompat.START)
                else drawerLayout.closeDrawer(GravityCompat.START)
            }
            val navhost = childFragmentManager.findFragmentById(R.id.drawer_nav_graph) as? NavHostFragment
            val controller = navhost?.navController
            navView.setupWithNavController(controller!!)

            gotoList = { id, title ->
                val bundle = Bundle().apply {
                    this.putInt("Class", id)
                    this.putString("Title", title)
                }
                findNavController().navigate(R.id.action_drawer_home_to_add_attendance_nav, bundle)
            }
        }
    }


    override fun getBinding(layoutInflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    companion object{
        lateinit var gotoList:  (Int, String) -> Unit
    }


}