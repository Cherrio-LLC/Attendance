package com.attendance.student.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.domain.student.StudentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class SignupViewModel @Inject constructor(private val studentRepo: StudentRepo) : ViewModel() {


    fun signup(fullname: String, matric: String, dept: String){
        viewModelScope.launch(Dispatchers.IO) {
            studentRepo.addStudent(StudentEntity(fullname = fullname,matricNo = matric,dept = dept))
        }
    }

}