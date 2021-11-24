package com.cherrio.attendance.presentation.home.attendee_list

import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.domain.model.ClassAndAttendance

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class AttendeeState(val list: MutableList<AttendeeEntity>? = null
)
