package com.attendance.student.presentation.mark

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
class ScannerViewModel @Inject constructor(
    private val studentRepo: StudentRepo,
    private val firebaseRepo: FirebaseRepo,
    private val attendancesRepo: AttendancesRepo
) : ViewModel() {
    private val _state = MutableStateFlow(ScannerState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                studentRepo.getStudent().collectLatest {
                    _state.value = ScannerState(it)
                }
            }
        }
    }

    fun markAttendance(classId: String, matricNo: String, latAndLon: LatAndLon) {
        _state.value = ScannerState(loading = true)
        viewModelScope.launch {
            firebaseRepo.getClassDetails(classId).collectLatest {
                if (it != null) {
                    val meters = getDistance(
                        latAndLon.lat,
                        latAndLon.lon,
                        it.latitude,
                        it.longitude
                    )
                    if (meters <= 20) {
                        firebaseRepo.markAttendance(classId, matricNo)
                        _state.value = ScannerState(loading = false, success = true)

                    }else{
                        _state.value = ScannerState(loading = false, error = "You are not in the range of the lecturer")
                    }
                }else{
                    _state.value = ScannerState(loading = false, error = "Class does not exists")
                }
            }
        }

    }

}