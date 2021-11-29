package com.attendance.student.presentation.attendances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.student.data.entity.AttendanceEntity
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.data.firebase.FirebaseRepo
import com.attendance.student.domain.attendances.AttendancesRepo
import com.attendance.student.domain.student.StudentRepo
import com.attendance.student.presentation.LatAndLon
import com.attendance.student.presentation.splash.SplashState
import com.attendance.student.utils.getDistance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class AttViewModel @Inject constructor(
    private val firebaseRepo: FirebaseRepo,
) : ViewModel() {
    private val _state = MutableStateFlow(AttState())
    val state = _state.asStateFlow()

    fun getClass(classId: String) {
        _state.value = AttState(loading = true)
        viewModelScope.launch {
            firebaseRepo.getClassDetails(classId).collectLatest {
                println("Class Details: $it")
                if (it != null) {
                    _state.value = AttState(loading = false, classDetails = it)

                }else{
                    _state.value = AttState(loading = false, error = "Class does not exists")
                }
            }
        }

    }
    
}