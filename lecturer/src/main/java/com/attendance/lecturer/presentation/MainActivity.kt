package com.attendance.lecturer.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.attendance.lecturer.databinding.ActivityMainBinding
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), Listener {

    private lateinit var easyWayLocation: EasyWayLocation
    private var gotten = false
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        easyWayLocation = EasyWayLocation(this, true, false, this)
        if (allPermissionsGranted()) {
            easyWayLocation.startLocation()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
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

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
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
            }
            else -> {

            }
        }
    }
    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == EasyWayLocation.LOCATION_SETTING_REQUEST_CODE) {
                easyWayLocation.onActivityResult(it.resultCode)
            }
        }

    override fun locationOn() {
        println("locationOn")
    }

    override fun currentLocation(location: Location?) {
        location?.let {
            viewModel.setLatAndLon(LatAndLon(it.latitude, it.longitude))
        }


    }

    override fun locationCancelled() {
        println("locationCancelled")
    }
}