package com.attendance.lecturer.di

import com.attendance.lecturer.domain.ui.AttendanceImpl
import com.attendance.lecturer.domain.ui.AttendanceRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindStudentRepo(attendanceImpl: AttendanceImpl): AttendanceRepo

}