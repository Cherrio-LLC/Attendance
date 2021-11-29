package com.attendance.lecturer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.data.entity.AttendeeEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Database(entities = [AttendanceEntity::class, AttendeeEntity::class], version = 1)
abstract class AttendanceDatabase: RoomDatabase() {
    abstract fun attendanceDao(): LecturerDAO
}