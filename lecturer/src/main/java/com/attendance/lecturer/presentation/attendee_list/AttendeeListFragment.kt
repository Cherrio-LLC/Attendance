package com.attendance.lecturer.presentation.attendee_list

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.text.Layout
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.attendance.lecturer.base.BaseFragment
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.databinding.FragmentAttendeeListBinding
import com.attendance.lecturer.utils.collectInView
import com.attendance.lecturer.utils.getOutputDirectory
import com.wwdablu.soumya.simplypdf.SimplyPdf
import com.wwdablu.soumya.simplypdf.composers.properties.TableProperties
import com.wwdablu.soumya.simplypdf.composers.properties.TextProperties
import com.wwdablu.soumya.simplypdf.composers.properties.cell.Cell
import com.wwdablu.soumya.simplypdf.composers.properties.cell.TextCell
import com.wwdablu.soumya.simplypdf.document.DocumentInfo
import com.wwdablu.soumya.simplypdf.document.Margin
import com.wwdablu.soumya.simplypdf.document.PageHeader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */


@AndroidEntryPoint
class AttendeeListFragment : BaseFragment<FragmentAttendeeListBinding>() {

    private val viewModel by viewModels<AttendeeListViewModel>()
    private val attendeeAdapter = AttendeeAdapter()
    private lateinit var file: File
    private lateinit var title: String

    override fun useViews() {
        handleBackPressed()
        val id = requireArguments().getInt("Class")
        title = requireArguments().getString("Title")!!
        viewModel.getAttendeeList(id)
        binding.apply {
            attendeeList.adapter = attendeeAdapter
            toolbar.title = "Class ID: $id"
            toolbar.setNavigationOnClickListener{
                findNavController().navigateUp()
            }
            btnEnd.setOnClickListener {
              viewModel.endAttendance(id)
            }
            btnExport.setOnClickListener {
                exportAttendance()
            }
        }
        attendeeAdapter.addItemClicked { entity ->
            viewModel.deleteAttendee(entity)
        }
        viewModel.state.collectInView(viewLifecycleOwner) {
            renderUi(it)
        }

    }

    private fun renderUi(state: AttendeeState) {
        with(state) {
            list?.let {
                it.forEach { att ->
                    attendeeAdapter.submitList(att)
                }
                binding.attendeeList.isVisible = attendeeAdapter.itemCount > 0
                binding.noItem.isVisible = attendeeAdapter.itemCount == 0

            }
            endedClass?.let {
                if (it){
                    Toast.makeText(requireContext(), "Attendance ended", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            }
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
    private fun exportAttendance() {
        file = File(getOutputDirectory(), "$title.pdf")
        generateIt(file)
    }


    private fun exportFile(file: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, file)
        resultLauncher.launch(intent)
    }


    override fun getBinding(layoutInflater: LayoutInflater): FragmentAttendeeListBinding {
        return FragmentAttendeeListBinding.inflate(layoutInflater)
    }

        private fun generateIt(file: File) {
        val header = listOf(TextCell(title, TextProperties().apply {
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

            attendeeAdapter.getList().forEachIndexed { index, classAttendee ->
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

}