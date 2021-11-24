package com.cherrio.attendance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltAndroidApp
class AttendanceApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}