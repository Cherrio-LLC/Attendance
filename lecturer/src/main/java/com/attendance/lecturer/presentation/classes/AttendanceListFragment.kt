package com.attendance.lecturer.presentation.classes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.print.PrintAttributes
import android.text.Layout
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attendance.lecturer.R
import com.attendance.lecturer.base.BaseFragment
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.databinding.DialogAddAttendanceBinding
import com.attendance.lecturer.databinding.FragmentClassesBinding
import com.attendance.lecturer.presentation.LatAndLon
import com.attendance.lecturer.presentation.MainViewModel
import com.attendance.lecturer.presentation.home.HomeFragment
import com.attendance.lecturer.utils.collectInView
import com.attendance.lecturer.utils.getOutputDirectory

import com.wwdablu.soumya.simplypdf.SimplyPdf
import com.wwdablu.soumya.simplypdf.SimplyPdfDocument
import com.wwdablu.soumya.simplypdf.composers.properties.TableProperties
import com.wwdablu.soumya.simplypdf.composers.properties.TextProperties
import com.wwdablu.soumya.simplypdf.composers.properties.cell.Cell
import com.wwdablu.soumya.simplypdf.composers.properties.cell.TextCell
import com.wwdablu.soumya.simplypdf.document.DocumentInfo
import com.wwdablu.soumya.simplypdf.document.Margin
import com.wwdablu.soumya.simplypdf.document.PageHeader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.io.*
import java.lang.StringBuilder
import java.util.*

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class AttendanceListFragment : BaseFragment<FragmentClassesBinding>() {

    private val viewModel by viewModels<AttendanceListViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val attendanceAdapter = AttendanceAdapter()
    private var dataToWrite = ""
    private lateinit var file: File
    private lateinit var latAndLon: LatAndLon


    override fun useViews() {
        binding.apply {
            recyclerView.adapter = attendanceAdapter

            btnAdd.setOnClickListener {
                addClass()
            }
        }
        attendanceAdapter.addItemClicked { classAndAttendance ->
            HomeFragment.gotoList(classAndAttendance.id, classAndAttendance.classTitle)
        }
        viewModel.state.collectInView(viewLifecycleOwner) {
            renderUi(it)
        }
        mainViewModel.state.observe(viewLifecycleOwner){
            latAndLon = it
        }

    }

    private fun renderUi(state: ListState) {
        with(state) {
            list?.let {
                attendanceAdapter.submitList(it)
                binding.noItem.isVisible = it.isEmpty()
            }
            loading?.let{
                binding.loading.isVisible = it
                binding.recyclerView.isVisible = !it
            }
            success?.let {
                HomeFragment.gotoList(it.id, it.title)
            }
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

        }
    }



    private fun addClass() {
        showDialog {
            viewModel.addClass(it, latAndLon)
        }
    }

    override fun getBinding(layoutInflater: LayoutInflater): FragmentClassesBinding {
        return FragmentClassesBinding.inflate(layoutInflater)
    }

    private inline fun showDialog(
        crossinline textListener: (String) -> Unit,
    ) {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val binding = DialogAddAttendanceBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setView(binding.root)
        binding.btnCreate.setOnClickListener {
            if (binding.matTitle.text!!.length > 5) {
                textListener.invoke(binding.matTitle.text.toString())
                dialog.dismiss()
            } else {
                binding.textField.error =  "Enter 5 or more character"
            }
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}
