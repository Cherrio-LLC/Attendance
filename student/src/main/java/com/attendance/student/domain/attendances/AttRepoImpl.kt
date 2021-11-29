package com.attendance.student.domain.attendances

import com.attendance.student.data.StudentDAO
import com.attendance.student.data.entity.AttendanceEntity
import com.attendance.student.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class AttRepoImpl @Inject constructor(private val dao: StudentDAO): AttendancesRepo {

    override fun getAttendances(): Flow<MutableList<AttendanceEntity>> {
        return dao.loadAttendances()
    }

    override suspend fun addAttendances(attendanceEntity: AttendanceEntity) {
       dao.addAttendance(attendanceEntity)
    }


}