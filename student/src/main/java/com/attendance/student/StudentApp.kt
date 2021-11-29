package com.attendance.student

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import org.acra.ReportField
import org.acra.config.dialog
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltAndroidApp
class StudentApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        initAcra {
            buildConfigClass = BuildConfig::class.java
            reportFormat = StringFormat.JSON
            reportContent = arrayOf(
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.BRAND,
                ReportField.STACK_TRACE,
                ReportField.PACKAGE_NAME,
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME
            )

            mailSender {
                mailTo = "ayodelekehinde@nqb8.co"
                body = "Error log sent"
                subject = "Attendance crash"
                reportFileName = "Crash.json"

            }
            dialog {
                title = "Attendance Crashed"
                text = "It crashed! We deeply regret this :| "
                commentPrompt = "Can you describe what you were doing when it crashed?"
                //resTheme = R.style.AttDialogTheme
                resIcon = R.drawable.ic_round_people
                negativeButtonText = "Cancel"
                positiveButtonText = "Send report"
            }
        }

    }
}