package com.attendance.student.presentation.attendances

import com.attendance.student.data.entity.StudentEntity
import com.attendance.student.data.firebase.ClassDetails

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class AttState(val classDetails: ClassDetails? = null,
                    val loading: Boolean? = null,
                    val error: String? = null
)
