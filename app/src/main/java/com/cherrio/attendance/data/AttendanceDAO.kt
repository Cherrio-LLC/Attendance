package com.cherrio.attendance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Dao
interface AttendanceDAO {

    @Query("SELECT * from attendanceentity JOIN attendeeentity ON attendanceentity.class_id = attendeeentity.class ORDER BY attendanceentity.class_id DESC")
    fun loadAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>>

    @Insert
    suspend fun addAttendance(attendanceEntity: AttendanceEntity)

    @Insert
    suspend fun addAttendee(attendeeEntity: AttendeeEntity)

    @Query("SELECT * from attendeeentity WHERE attendeeentity.class = :id")
    fun loadAttendees(id: String): Flow<MutableList<AttendeeEntity>>

    @Delete
    suspend fun deleteAttendee(attendeeEntity: AttendeeEntity)


}