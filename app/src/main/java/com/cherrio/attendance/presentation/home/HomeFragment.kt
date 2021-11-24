package com.cherrio.attendance.presentation.home

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.databinding.FragmentHomeBinding
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
    }
}