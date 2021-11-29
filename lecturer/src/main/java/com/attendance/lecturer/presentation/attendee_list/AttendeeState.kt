package com.attendance.lecturer.presentation.attendee_list

import com.attendance.lecturer.data.entity.AttendeeEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class AttendeeState(val list: MutableList<AttendeeEntity>? = null,
                         val endedClass: Boolean? = null
)
