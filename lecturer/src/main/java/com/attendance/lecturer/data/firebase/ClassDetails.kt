package com.attendance.student.data.firebase

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ClassDetails(
    val className: String,
    val classId: Int,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", 0,0.0, 0.0)
}

