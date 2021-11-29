package com.attendance.lecturer.presentation.classes

import com.attendance.lecturer.data.entity.AttendanceEntity

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ListState(val list: List<AttendanceEntity>? = null,
                     val loading: Boolean? = null,
                     val success: Succ? = null,
                     val error: String? = null,
                     val title: String? = null
){
    data class Succ(val id: Int,val title: String)
}
