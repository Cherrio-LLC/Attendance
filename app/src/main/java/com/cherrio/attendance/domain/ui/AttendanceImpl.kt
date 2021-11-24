package com.cherrio.attendance.domain.ui

import com.cherrio.attendance.data.AttendanceDAO
import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.data.mapToEntity
import com.cherrio.attendance.di.DataModule
import com.cherrio.attendance.domain.model.Attendee
import com.cherrio.attendance.domain.model.ClassAttendance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class AttendanceImpl @Inject constructor(private val dao: AttendanceDAO): AttendanceRepo {

    override suspend fun addAttendance(classAttendance: ClassAttendance) {
        dao.addAttendance(classAttendance.mapToEntity())
    }

    override suspend fun addAttendee(attendee: Attendee) {
        dao.addAttendee(attendee.mapToEntity())
    }

    override fun getAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>> {
        return dao.loadAttendance()
    }

    override fun getAttendees(id: String): Flow<MutableList<AttendeeEntity>> {
        return dao.loadAttendees(id)
    }

    override suspend fun deleteAttendee(attendee: AttendeeEntity){
        return dao.deleteAttendee(attendee)
    }

}