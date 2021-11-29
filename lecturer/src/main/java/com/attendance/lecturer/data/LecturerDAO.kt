package com.attendance.lecturer.data

import androidx.room.*
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.data.entity.AttendeeEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Dao
interface LecturerDAO {

    @Query("SELECT * from attendanceentity JOIN attendeeentity ON attendanceentity.class_id = attendeeentity.class ORDER BY attendanceentity.class_id DESC")
    fun loadAttendance(): Flow<Map<AttendanceEntity, List<AttendeeEntity>>>

    @Query("SELECT * from attendanceentity ORDER BY attendanceentity.class_id DESC")
    fun loadEmptyAttendance(): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendance(attendanceEntity: AttendanceEntity)

    @Insert
    suspend fun addAttendee(attendeeEntity: AttendeeEntity)

    @Query("SELECT * from attendeeentity WHERE attendeeentity.class = :id")
    fun loadAttendees(id: String): Flow<MutableList<AttendeeEntity>>

    @Query("SELECT * from attendeeentity WHERE attendeeentity.matric_no = :matric AND attendeeentity.class = :classId")
    fun getAttendee(matric: String, classId: String): List<AttendeeEntity>

    @Delete
    suspend fun deleteAttendee(attendeeEntity: AttendeeEntity)


}