package com.attendance.lecturer.utils

import androidx.fragment.app.Fragment
import com.attendance.lecturer.R
import java.io.File

/**
 *Created by Ayodele on 11/24/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
const val SELFIE = "selfie.png"
const val GOVT = "govt.png"

fun Fragment.getOutputDirectory(): File {
    val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else requireActivity().filesDir.apply {
        File(this,"cache/images").apply { mkdirs() }
    }
}