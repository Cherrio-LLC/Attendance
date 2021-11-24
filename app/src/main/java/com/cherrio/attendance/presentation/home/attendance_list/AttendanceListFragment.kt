package com.cherrio.attendance.presentation.home.attendance_list

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.databinding.DialogAddAttendanceBinding
import com.cherrio.attendance.databinding.FragmentAttendanceListBinding
import com.cherrio.attendance.domain.model.ClassAndAttendance
import com.cherrio.attendance.utils.collectInView
import com.cherrio.attendance.utils.getOutputDirectory
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.PDFTableView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.lang.StringBuilder

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class AttendanceListFragment : BaseFragment<FragmentAttendanceListBinding>() {

    private val viewModel by viewModels<AttendanceListViewModel>()
    private val attendanceAdapter = AttendanceAdapter()
    private var dataToWrite = ""


    override fun useViews() {
        binding.apply {
            recyclerView.adapter = attendanceAdapter

            btnAdd.setOnClickListener {
                addClass()
            }
        }
        attendanceAdapter.addItemClicked { classAndAttendance, export ->
            if (export) {
                exportAttendance(classAndAttendance)
            } else {
                val bundle = Bundle().apply {
                    this.putString("Class", classAndAttendance.id)
                }
                findNavController().navigate(
                    R.id.action_drawer_home_to_attendeeListFragment,
                    bundle
                )
            }
        }
        viewModel.state.collectInView(viewLifecycleOwner) {
            renderUi(it)
        }

    }

    private fun renderUi(state: ListState) {
        println("State: $state")
        with(state) {
            list?.let {
                attendanceAdapter.submitList(it)
            }
            title?.let {

            }
        }
    }

    private fun exportAttendance(classAndAttendance: ClassAndAttendance) {
        generatePdf(classAndAttendance)
    }

    private fun generatePdf(classAndAttendance: ClassAndAttendance) {
        val head = classAndAttendance.name
        val builder = StringBuilder(head).append("\n").append("\n")
        builder.append("SN           Matriculation No").append("\n")
        classAndAttendance.attendees.forEachIndexed { index, attendeeEntity ->
            val no = index + 1
            builder.append("$no           ${attendeeEntity.matricNo}").append("\n")
        }
        dataToWrite = builder.toString()
        exportFile("${classAndAttendance.name}.txt")
    }

    private fun exportFile(file: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TITLE, file)
        resultLauncher.launch(intent)
    }

    private fun addClass() {
        showDialog {
            val id = System.currentTimeMillis().toString()
            viewModel.addClass(it, id)
            val bundle = Bundle().apply {
                this.putString("Class", id)
            }
            findNavController().navigate(R.id.action_drawer_home_to_add_attendance_nav, bundle)
        }
    }

    override fun getBinding(layoutInflater: LayoutInflater): FragmentAttendanceListBinding {
        return FragmentAttendanceListBinding.inflate(layoutInflater)
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
                Toast.makeText(context, "Enter 5 or more character", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val path = data!!.data!!
                val output = requireActivity().contentResolver.openOutputStream(path)
                val writer = OutputStreamWriter(output)
                try {
                    writer.use {
                        it.write(dataToWrite)
                    }
                    Toast.makeText(requireContext(), "Attendance generated successfully", Toast.LENGTH_SHORT).show()
                }catch (e: Exception){

                }

            }
        }

}