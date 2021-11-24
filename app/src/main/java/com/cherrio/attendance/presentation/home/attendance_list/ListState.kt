package com.cherrio.attendance.presentation.home.attendance_list

import com.cherrio.attendance.domain.model.ClassAndAttendance

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ListState(val list: MutableList<ClassAndAttendance>? = null,
                     val title: String? = null
)
