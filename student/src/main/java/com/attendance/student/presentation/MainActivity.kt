package com.attendance.student.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.attendance.student.databinding.ActivityMainBinding
import com.attendance.student.utils.getDistance
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE
import com.example.easywaylocation.Listener
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), Listener {

    private lateinit var easyWayLocation: EasyWayLocation
    private var gotten = false
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        easyWayLocation = EasyWayLocation(this,true,false, this)
        if (allPermissionsGranted()){
            easyWayLocation.startLocation()
        }else{
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }


    }


    override fun onPause() {
        super.onPause()
        easyWayLocation.endUpdates()
    }

    override fun onResume() {
        super.onResume()
        easyWayLocation.startLocation()
    }

    companion object{
        const val DEEPLINK = "attendance://HomeFragment"
        internal lateinit var goBack: () -> Unit
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        println(permissions)
        when {
            permissions.get(Manifest.permission.ACCESS_FINE_LOCATION) ?: false -> {
                // Precise location access granted.
            }
            permissions.get(Manifest.permission.ACCESS_COARSE_LOCATION) ?: false -> {
                // Only approximate location access granted.
            } else -> {

        }
        }
    }
    private val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == LOCATION_SETTING_REQUEST_CODE){
            easyWayLocation.onActivityResult(it.resultCode)
        }
    }

    override fun locationOn() {
        println("locationOn")
    }

    override fun currentLocation(location: Location?) {
        location?.let {
            getDistance(location.latitude,
                location.longitude,
                7.1665134,
                5.231455
            )
            viewModel.setLatAndLon(LatAndLon(it.latitude, it.longitude))
        }


    }

    override fun locationCancelled() {
        println("locationCancelled")
    }
    private fun showConfirmation(longitude: String, latitude: String){
        if (!gotten) {
            gotten = true
            MaterialAlertDialogBuilder(
                this,
                R.style.MaterialAlertDialog_MaterialComponents
            )
                .setTitle("Captured Location")
                .setMessage("Longitude: $longitude \nLatitude: $latitude")
                .setNegativeButton("Done") { dialog, which ->
                }
                .setPositiveButton("Continue") { dialog, _ ->

                }.show()
        }

    }


}