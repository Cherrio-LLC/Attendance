package com.cherrio.attendance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@Entity
data class AttendanceEntity(
    @PrimaryKey
    @ColumnInfo(name = "class_id") val id: String,
    @ColumnInfo(name = "class_title") val classTitle: String
)
