package com.cherrio.attendance.presentation.home.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherrio.attendance.domain.model.Attendee
import com.cherrio.attendance.domain.ui.AttendanceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class ScannerViewModel @Inject constructor(private val attendanceRepo: AttendanceRepo) : ViewModel() {


    fun setScannedText(text: String, id: String){
        viewModelScope.launch {
            attendanceRepo.addAttendee(Attendee(id, text))
        }
    }
}