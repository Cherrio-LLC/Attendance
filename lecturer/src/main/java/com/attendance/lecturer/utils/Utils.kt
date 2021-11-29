package com.attendance.lecturer.utils

import kotlin.random.Random

object Utils {
    private val ALPHA_NUMERIC = listOf('0','1','2','3','4','5','6','7','8','9')
    private const val LENGTH = 5

    fun generateId(): String {
        return List(LENGTH) { Random.nextInt(0, ALPHA_NUMERIC.size) }
            .map { ALPHA_NUMERIC[it] }
            .joinToString(separator = "")
    }
}