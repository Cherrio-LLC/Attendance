package com.attendance.student.presentation.mark

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.attendance.student.base.BaseFragment
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.databinding.FragmentScannerBinding
import com.attendance.student.presentation.LatAndLon
import com.attendance.student.presentation.MainViewModel
import com.attendance.student.utils.collectInView
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.google.android.material.R
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
    private val mainViewModel by activityViewModels<MainViewModel>()

    private var dataId = 0
    private lateinit var mStudentEntity: StudentEntity
    private lateinit var latAndLon: LatAndLon

    override fun useViews() {
        handleBackPressed()
        val classTitle = requireArguments().getInt("Class")!!
        binding.apply {
            btnMark.setOnClickListener {
                viewModel.markAttendance(classTitle.toString(), mStudentEntity.matricNo, latAndLon)
            }
        }
        viewModel.state.collectInView(viewLifecycleOwner){
            with(it){
                studentEntity?.let { s ->
                   mStudentEntity = s
                }
                loading?.let {
                    binding.apply {
                        loading.isVisible = it
                        btnMark.isVisible = !it
                    }
                }
                error?.let { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
                success?.let {
                    showDone()
                }
            }
        }
        mainViewModel.state.observe(viewLifecycleOwner){
            latAndLon = it
        }
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                if (mStudentEntity.matricNo == it.text){
                    binding.apply {
                        matName.text = mStudentEntity.fullname
                        matMat.text = mStudentEntity.matricNo
                        matDept.text = mStudentEntity.dept
                        btnMark.isVisible = true
                    }
                }else{
                   showError()
                }
            }
        }
        startCamera()
    }
    private fun showError(){
        MaterialAlertDialogBuilder(requireContext(),
            R.style.MaterialAlertDialog_MaterialComponents)
            .setTitle("Error")
            .setMessage("Matric Number doesn't match")
            .setPositiveButton("Try again") { dialog, _ ->
                // continue
                dialog.dismiss()
                startCamera()

            }
            .show()
    }
    private fun showDone(){
        MaterialAlertDialogBuilder(requireContext(),
            R.style.MaterialAlertDialog_MaterialComponents)
            .setTitle("Success")
            .setMessage("Attendance successfully marked")
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()

            }.show()
    }


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