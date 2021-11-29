package com.attendance.lecturer.presentation.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.domain.ui.AttendanceRepo
import com.attendance.lecturer.presentation.LatAndLon
import com.attendance.lecturer.utils.Utils
import com.attendance.student.data.firebase.ClassDetails
import com.attendance.student.data.firebase.FirebaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class AttendanceListViewModel @Inject constructor(private val attendanceRepo: AttendanceRepo,
                                                  private val firebaseRepo: FirebaseRepo,
) : ViewModel() {
    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    init {
        getAttendanceList()
    }
    private fun getAttendanceList(){
        viewModelScope.launch {
            attendanceRepo.loadEmptyAttendance().collectLatest {
                _state.value = ListState(list = it)
            }
        }
    }

    fun addClass(title: String, latAndLon: LatAndLon){
        _state.value = ListState(loading = true)
        val id = Utils.generateId()
        val date = System.currentTimeMillis().toDate()
        val classDetails = ClassDetails(title, id.toInt(), latAndLon.lat, latAndLon.lon)
        val classAttendance = AttendanceEntity(id.toInt(), title.plus("(").plus(date).plus(")"))
        viewModelScope.launch{
            firebaseRepo.createClass(classDetails).collectLatest {
                if (it){
                    attendanceRepo.addAttendance(classAttendance)
                    _state.value = ListState(loading = false, success = ListState.Succ(id.toInt(),title))
                }else{
                    _state.value = ListState(loading = false, error = "Class creation failed")
                }
            }
        }
    }

    private fun Long.toDate(): String {
        val parser = SimpleDateFormat("dd-MM-yyy", Locale.getDefault())
        return parser.format(Date(this))
    }
}