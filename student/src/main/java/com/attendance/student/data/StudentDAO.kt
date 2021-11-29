package com.attendance.student.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.attendance.student.data.entity.AttendanceEntity
import com.attendance.student.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Dao
interface StudentDAO {

    @Query("SELECT * from studententity")
    fun getStudent(): Flow<StudentEntity>

    @Insert
    suspend fun addStudent(studentEntity: StudentEntity)

    @Insert
    suspend fun addAttendance(attendanceEntity: AttendanceEntity)


    @Query("SELECT * from attendanceentity ORDER BY attendanceentity.id DESC")
    fun loadAttendances(): Flow<MutableList<AttendanceEntity>>


}