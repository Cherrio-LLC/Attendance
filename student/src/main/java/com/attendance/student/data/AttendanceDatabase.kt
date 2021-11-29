package com.attendance.student.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.attendance.student.data.entity.AttendanceEntity
import com.attendance.student.data.entity.StudentEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Database(entities = [AttendanceEntity::class, StudentEntity::class], version = 1)
abstract class AttendanceDatabase: RoomDatabase() {
    abstract fun attendanceDao(): StudentDAO
}