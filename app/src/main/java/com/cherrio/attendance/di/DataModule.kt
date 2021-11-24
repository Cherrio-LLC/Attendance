package com.cherrio.attendance.di

import android.content.Context
import androidx.room.Room
import com.cherrio.attendance.data.AttendanceDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AttendanceDatabase::class.java, "attendance_database").build()

    @Provides
    @Singleton
    fun provideDao(db: AttendanceDatabase) = db.attendanceDao()


}