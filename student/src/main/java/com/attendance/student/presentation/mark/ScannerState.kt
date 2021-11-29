package com.attendance.student.presentation.mark

import com.attendance.student.data.entity.StudentEntity

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class ScannerState(val studentEntity: StudentEntity? = null,
                        val loading: Boolean? = null,
                        val error: String? = null,
                        val success: Boolean? = null
)
