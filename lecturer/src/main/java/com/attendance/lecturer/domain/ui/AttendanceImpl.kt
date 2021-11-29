package com.attendance.lecturer.domain.ui


import com.attendance.lecturer.data.LecturerDAO
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.data.entity.AttendeeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class AttendanceImpl @Inject constructor(private val dao: LecturerDAO): AttendanceRepo {

    override suspend fun addAttendance(attendanceEntity: AttendanceEntity) {
        dao.addAttendance(attendanceEntity)
    }

    override suspend fun addAttendee(attendee: AttendeeEntity) {
        dao.addAttendee(attendee)
    }

    override fun getAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>> {
        return dao.loadAttendance()
    }

    override fun loadEmptyAttendance(): Flow<List<AttendanceEntity>> {
        return dao.loadEmptyAttendance()
    }

    override fun getAttendees(id: String): Flow<MutableList<AttendeeEntity>> {
        return dao.loadAttendees(id)
    }

    override suspend fun deleteAttendee(attendee: AttendeeEntity){
        return dao.deleteAttendee(attendee)
    }

    override fun getAttendee(matric: String,  classId: String): List<AttendeeEntity> {
        return dao.getAttendee(matric, classId)
    }

}