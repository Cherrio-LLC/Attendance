package com.attendance.student.di

import com.attendance.student.domain.attendances.AttRepoImpl
import com.attendance.student.domain.attendances.AttendancesRepo
import com.attendance.student.domain.student.StudentRepo
import com.attendance.student.domain.student.StudentRepoImpl
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
    abstract fun bindStudentRepo(studentRepoImpl: StudentRepoImpl): StudentRepo

    @Binds
    abstract fun bindAttendanceRepo(attRepoImpl: AttRepoImpl): AttendancesRepo
}