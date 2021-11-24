package com.cherrio.attendance.domain.ui

import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.domain.model.Attendee
import com.cherrio.attendance.domain.model.ClassAttendance
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface AttendanceRepo {
    suspend fun addAttendance(classAttendance: ClassAttendance)
    suspend fun addAttendee(attendee: Attendee)
    fun getAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>>
    fun getAttendees(id: String): Flow<MutableList<AttendeeEntity>>
    suspend fun deleteAttendee(attendee: AttendeeEntity)
}