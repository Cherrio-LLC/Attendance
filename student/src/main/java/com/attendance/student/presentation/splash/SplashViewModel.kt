package com.attendance.student.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.domain.student.StudentRepo
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
class SplashViewModel @Inject constructor(private val studentRepo: StudentRepo) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            studentRepo.getStudent().collectLatest {
                if (it != null) {
                    _state.value = SplashState(it)
                }else{
                    _state.value = SplashState(StudentEntity(0,"","",""))
                }
            }
        }
    }


}