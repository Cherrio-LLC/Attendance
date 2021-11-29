package com.attendance.lecturer.domain.model

import com.attendance.lecturer.data.entity.AttendeeEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ClassAndAttendance(val name: String,
                              val id: String,
                              val attendees: List<AttendeeEntity>)
