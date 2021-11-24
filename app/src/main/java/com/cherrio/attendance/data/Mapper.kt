package com.cherrio.attendance.data

import com.cherrio.attendance.data.entity.AttendanceEntity
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.domain.model.Attendee
import com.cherrio.attendance.domain.model.ClassAttendance

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun ClassAttendance.mapToEntity(): AttendanceEntity{
    return AttendanceEntity(id = _id,
        classTitle = this.name
    )
}
fun Attendee.mapToEntity(): AttendeeEntity{
    return AttendeeEntity(
        matricNo = matricNo,
        classId = classId
    )
}