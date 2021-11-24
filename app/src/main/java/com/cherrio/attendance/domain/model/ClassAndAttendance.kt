package com.cherrio.attendance.domain.model

import com.cherrio.attendance.data.entity.AttendeeEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ClassAndAttendance(val name: String,
                              val id: String,
                              val attendees: List<AttendeeEntity>)
