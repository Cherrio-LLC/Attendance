package com.attendance.student.domain.student

import com.attendance.student.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface StudentRepo {

    fun getStudent(): Flow<StudentEntity>
    suspend fun addStudent(studentEntity: StudentEntity)
}