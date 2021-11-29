package com.attendance.student.utils

import android.location.Location
import kotlin.math.roundToLong

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun getDistance(studentLat: Double,
                studentLon: Double,
                lecturerLat: Double,
                lecturerLon: Double
): Long{
    val startPoint = Location("student")
    startPoint.setLatitude(studentLat)
    startPoint.setLongitude(studentLon)

    val endPoint = Location("lecturer")
    endPoint.setLatitude(lecturerLat)
    endPoint.setLongitude(lecturerLon)

    val distance: Float = startPoint.distanceTo(endPoint)
    return distance.roundToLong()
}