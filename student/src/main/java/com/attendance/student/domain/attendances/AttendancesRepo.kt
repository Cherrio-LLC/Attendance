package com.attendance.student.domain.attendances

import com.attendance.student.data.entity.AttendanceEntity
import com.attendance.student.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface AttendancesRepo {
    fun getAttendances(): Flow<MutableList<AttendanceEntity>>
    suspend fun addAttendances(attendanceEntity: AttendanceEntity)

}