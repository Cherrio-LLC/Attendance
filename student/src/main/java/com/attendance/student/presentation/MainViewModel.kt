package com.attendance.student.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.domain.attendances.AttendancesRepo
import com.attendance.student.domain.student.StudentRepo
import com.attendance.student.presentation.splash.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableLiveData<LatAndLon>()
    val state: LiveData<LatAndLon> = _state


    fun setLatAndLon(latAndLon: LatAndLon){
        _state.value = latAndLon
    }


}