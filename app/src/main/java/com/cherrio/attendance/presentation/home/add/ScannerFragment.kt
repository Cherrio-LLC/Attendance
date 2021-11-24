package com.cherrio.attendance.presentation.home.add

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.databinding.FragmentAttendanceListBinding
import com.cherrio.attendance.databinding.FragmentScannerBinding
import com.cherrio.attendance.presentation.home.HomeFragment
import com.cherrio.attendance.presentation.home.dialog.AddAttendanceDialog
import com.cherrio.attendance.utils.collectInView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class ScannerFragment : BaseFragment<FragmentScannerBinding>() {
    private lateinit var codeScanner: CodeScanner
    private val viewModel by viewModels<ScannerViewModel>()
    private val addedAttendeeAdapter = AddedAttendeeAdapter()
    private var dataId = 0

    override fun useViews() {
        handleBackPressed()
        val classTitle = requireArguments().getString("Class")!!
        binding.apply {
            addedList.adapter = addedAttendeeAdapter
            addedList.layoutManager = LinearLayoutManager(requireContext())
            btnDone.setOnClickListener {
                goBack()
            }
        }
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                viewModel.setScannedText(it.text, classTitle)
                addedAttendeeAdapter.submitList(AttendeeEntity(dataId++,it.text, classTitle))
                startCamera()
            }
        }
        startCamera()
    }
//    private fun showConfirmation(text: String){
//        MaterialAlertDialogBuilder(requireContext(),
//            com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents)
//            .setTitle("Captured Student")
//            .setMessage("Matric Number: $text")
//            .setNegativeButton("Done") { dialog, which ->
//                goBack()
//            }
//            .setPositiveButton("Continue") { dialog, _ ->
//                // continue
//                dialog.dismiss()
//                startCamera()
//
//            }
//            .show()
//    }


    override fun getBinding(layoutInflater: LayoutInflater): FragmentScannerBinding {
        return FragmentScannerBinding.inflate(layoutInflater)
    }
    private fun startCamera(){
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }else{
            codeScanner.startPreview()
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

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                codeScanner.startPreview()
            } else {
                Toast.makeText(requireContext(), "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}