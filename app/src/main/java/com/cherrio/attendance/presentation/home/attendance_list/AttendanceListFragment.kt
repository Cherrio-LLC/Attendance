package com.cherrio.attendance.presentation.home.attendance_list

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cherrio.attendance.R
import com.cherrio.attendance.base.BaseFragment
import com.cherrio.attendance.databinding.DialogAddAttendanceBinding
import com.cherrio.attendance.databinding.FragmentAttendanceListBinding
import com.cherrio.attendance.domain.model.ClassAndAttendance
import com.cherrio.attendance.utils.collectInView
import com.cherrio.attendance.utils.doIt
import com.cherrio.attendance.utils.getOutputDirectory
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
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.*
import java.lang.StringBuilder
import java.util.*

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class AttendanceListFragment : BaseFragment<FragmentAttendanceListBinding>() {

    private val viewModel by viewModels<AttendanceListViewModel>()
    private val attendanceAdapter = AttendanceAdapter()
    private var dataToWrite = ""
    private lateinit var file: File


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
                binding.noItem.isVisible = it.size == 0
            }
            title?.let {

            }
        }
    }

    private fun exportAttendance(classAndAttendance: ClassAndAttendance) {
        file = File(getOutputDirectory(), "${classAndAttendance.name}.pdf")
        generateIt(file, classAndAttendance)
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
        intent.type = "application/pdf"
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
                data!!.data?.also { uri ->
                    write(file, uri)
                }

            }
        }

    private fun generateIt(file: File, classAndAttendance: ClassAndAttendance) {
        val header = listOf(TextCell(classAndAttendance.name, TextProperties().apply {
            textSize = 18
            textColor = "#000000"
            alignment = Layout.Alignment.ALIGN_CENTER
        }, Cell.MATCH_PARENT))
        val pdf = SimplyPdf.with(requireContext(), file)
            .colorMode(DocumentInfo.ColorMode.COLOR)
            .pageModifier(PageHeader(header))
            .paperSize(PrintAttributes.MediaSize.ISO_A4)
            .margin(Margin.default)
            .firstPageBackgroundColor(Color.WHITE)
            .paperOrientation(DocumentInfo.Orientation.PORTRAIT)
            .build()

        val rows = LinkedList<LinkedList<Cell>>().apply {

            //Row - 1
            add(LinkedList<Cell>().apply {

                //Column - 1
                add(TextCell("SN", TextProperties().apply {
                    textSize = 13
                    textColor = "#000000"
                }, pdf.usablePageWidth / 3))

                add(TextCell("Matriculation No", TextProperties().apply {
                    textSize = 13
                    textColor = "#000000"
                }, pdf.usablePageWidth / 2))

            })

            classAndAttendance.attendees.forEachIndexed { index, classAttendee ->
                add(LinkedList<Cell>().apply {

                    //Column - 1
                    val no = index + 1
                    add(TextCell(no.toString(), TextProperties().apply {
                        textSize = 13
                        textColor = "#000000"
                    }, pdf.usablePageWidth / 3))


                    add(TextCell(classAttendee.matricNo, TextProperties().apply {
                        textSize = 13
                        textColor = "#000000"
                    }, pdf.usablePageWidth / 2))

                })
            }
        }
        pdf.table.draw(rows, TableProperties().apply {
            borderColor = "#000000"
            borderWidth = 2
            drawBorder = true
        })
        runBlocking {
            pdf.finish()
            exportFile(file.name)
        }

    }

    private fun write(file: File, uri: Uri) {
        try {
            val inputStream = requireActivity().contentResolver.openOutputStream(uri)
            val oStream = FileInputStream(file)
            oStream.let { stream ->
                val buf = ByteArray(8192)
                var length: Int
                while (stream.read(buf).also { length = it } > 0) {
                    inputStream!!.write(buf, 0, length)
                }
            }
            inputStream!!.flush()
            Toast.makeText(
                requireContext(),
                "Attendance generated successfully",
                Toast.LENGTH_SHORT
            ).show()
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error occured", Toast.LENGTH_SHORT).show()
        }
    }

}