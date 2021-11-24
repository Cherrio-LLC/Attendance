package com.cherrio.attendance.presentation.home.attendee_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.domain.model.ClassAndAttendance
import com.cherrio.attendance.domain.model.ClassAttendance
import com.cherrio.attendance.domain.ui.AttendanceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class AttendeeListViewModel @Inject constructor(private val attendanceRepo: AttendanceRepo) : ViewModel() {
    private val _state = MutableStateFlow(AttendeeState())
    val state = _state.asStateFlow()

     fun getAttendeeList(id: String){
        viewModelScope.launch {
            attendanceRepo.getAttendees(id).collectLatest {
                _state.value = AttendeeState(it)
            }
        }
    }
    fun deleteAttendee(attendeeEntity: AttendeeEntity){
        viewModelScope.launch {
            attendanceRepo.deleteAttendee(attendeeEntity)
        }
    }
}