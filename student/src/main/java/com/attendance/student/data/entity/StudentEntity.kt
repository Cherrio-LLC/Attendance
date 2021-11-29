package com.attendance.student.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@Entity
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "full_name") val fullname: String,
    @ColumnInfo(name = "matric") val matricNo: String,
    @ColumnInfo(name = "dept") val dept: String

)
