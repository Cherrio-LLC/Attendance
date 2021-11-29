package com.attendance.student.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.attendance.student.R
import com.attendance.student.base.BaseFragment
import com.attendance.student.databinding.FragmentHomeBinding
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
        }
    }


    override fun getBinding(layoutInflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    companion object{
        const val DEEPLINK = "attendance://HomeFragment"
        internal lateinit var goBack: () -> Unit
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    }

}