package com.cherrio.attendance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Database(entities = [AttendanceEntity::class, AttendeeEntity::class], version = 1)
abstract class AttendanceDatabase: RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDAO
}