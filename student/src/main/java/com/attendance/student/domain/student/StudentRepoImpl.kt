package com.attendance.student.domain.student

import com.attendance.student.data.StudentDAO
import com.attendance.student.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class StudentRepoImpl @Inject constructor(private val dao: StudentDAO): StudentRepo {

    override fun getStudent(): Flow<StudentEntity> {
        return dao.getStudent()
    }

    override suspend fun addStudent(studentEntity: StudentEntity) {
        dao.addStudent(studentEntity)
    }
}