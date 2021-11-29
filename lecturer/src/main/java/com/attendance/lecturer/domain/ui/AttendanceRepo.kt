package com.attendance.lecturer.domain.ui

import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.data.entity.AttendeeEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface AttendanceRepo {

    suspend fun addAttendance(attendanceEntity: AttendanceEntity)

    suspend fun addAttendee(attendee: AttendeeEntity)

    fun getAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>>

    fun loadEmptyAttendance(): Flow<List<AttendanceEntity>>


    fun getAttendees(id: String): Flow<MutableList<AttendeeEntity>>

    suspend fun deleteAttendee(attendee: AttendeeEntity)

    fun getAttendee(matric: String, classId: String): List<AttendeeEntity>

}