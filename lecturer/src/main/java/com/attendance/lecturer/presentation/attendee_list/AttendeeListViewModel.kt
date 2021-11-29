package com.attendance.lecturer.presentation.attendee_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.lecturer.data.entity.AttendeeEntity
import com.attendance.lecturer.domain.ui.AttendanceRepo
import com.attendance.student.data.firebase.FirebaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
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
class AttendeeListViewModel @Inject constructor(private val attendanceRepo: AttendanceRepo,
                                                private val firebaseRepo: FirebaseRepo
) : ViewModel() {
    private val _state = MutableStateFlow(AttendeeState())
    val state = _state.asStateFlow()


    private fun getAttendee(id: Int){
        viewModelScope.launch {
            attendanceRepo.getAttendees(id.toString()).collectLatest {
                _state.value = AttendeeState(list = it)
            }
        }
    }

     fun getAttendeeList(id: Int){
         getAttendee(id)
        GlobalScope.launch {
            firebaseRepo.getClassAttendances(id.toString()).collectLatest {
                if (it != null){
                    val list = attendanceRepo.getAttendee(it.matricNo, id.toString())
                    if (list.isEmpty()) {
                        attendanceRepo.addAttendee(
                            AttendeeEntity(
                                matricNo = it.matricNo,
                                classId = id.toString()
                            )
                        )
                    }
                    firebaseRepo.deleteAttendance(id.toString(), it)
                }
            }
        }
    }
    fun deleteAttendee(attendeeEntity: AttendeeEntity){
        viewModelScope.launch {
            attendanceRepo.deleteAttendee(attendeeEntity)
        }
    }
    fun endAttendance(id: Int){
        viewModelScope.launch {
            firebaseRepo.deleteClass(id.toString()).collectLatest {
                _state.value = AttendeeState(endedClass = it)
            }
        }
    }
}