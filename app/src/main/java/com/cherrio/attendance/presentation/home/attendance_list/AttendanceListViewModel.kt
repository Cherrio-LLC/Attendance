package com.cherrio.attendance.presentation.home.attendance_list

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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class AttendanceListViewModel @Inject constructor(private val attendanceRepo: AttendanceRepo) : ViewModel() {
    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    init {
        getAttendanceList()
    }
    private fun getAttendanceList(){
        viewModelScope.launch {
            attendanceRepo.getAttendance().collectLatest {
                parseAttendance(it)
            }
        }
    }
    private fun parseAttendance(list: Map<AttendanceEntity, List<AttendeeEntity>>){
        val classAndAttendance = mutableListOf<ClassAndAttendance>()
        list.forEach {
            val c = ClassAndAttendance(it.key.classTitle, it.key.id, it.value)
            classAndAttendance.add(c)
        }
        _state.value = ListState(list = classAndAttendance)
    }
    fun addClass(title: String, id: String){
        val date = id.toLong().toDate()
        val classAttendance = ClassAttendance(id, title.plus("\n(").plus(date).plus(")"))
        viewModelScope.launch{
            attendanceRepo.addAttendance(classAttendance)
            _state.value = ListState(title = title)
        }
    }
    fun Long.toDate(): String {
        val parser = SimpleDateFormat("dd-MM-yyy", Locale.getDefault())
        return parser.format(Date(this))
    }
}