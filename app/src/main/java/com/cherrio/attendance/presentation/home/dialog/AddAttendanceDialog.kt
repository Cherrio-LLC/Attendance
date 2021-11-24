package com.cherrio.attendance.presentation.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cherrio.attendance.R
import com.cherrio.attendance.databinding.DialogAddAttendanceBinding

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class AddAttendanceDialog: DialogFragment() {

    private lateinit var binding: DialogAddAttendanceBinding
    private lateinit var textListener: (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddAttendanceBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBttmSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       useView()
    }
    private fun useView(){
        binding.apply {
            btnCancel.setOnClickListener {
                dismissAllowingStateLoss()
            }
            btnCreate.setOnClickListener {
                if (matTitle.text!!.length > 5){
                    textListener.invoke(matTitle.text.toString())
                    dismissAllowingStateLoss()
                }else{
                    Toast.makeText(context, "Enter 5 or more character", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun addListener(textListener: (String) -> Unit): AddAttendanceDialog{
       this.textListener = textListener
        return this
    }
}