package com.cherrio.attendance.presentation

import android.content.Context
import ru.skornei.restserver.annotations.Accept
import ru.skornei.restserver.annotations.ExceptionHandler
import ru.skornei.restserver.annotations.Produces
import ru.skornei.restserver.annotations.RestController
import ru.skornei.restserver.annotations.methods.GET
import ru.skornei.restserver.annotations.methods.POST
import ru.skornei.restserver.server.dictionary.ContentType
import ru.skornei.restserver.server.protocol.RequestInfo
import ru.skornei.restserver.server.protocol.ResponseInfo
import java.io.PrintWriter
import java.io.StringWriter

/**
 *Created by Ayodele on 11/25/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@RestController("/send")
class AttendanceApi {

    @GET
    @Produces(ContentType.TEXT_PLAIN)
    fun getSend(): String {
        println("Worked!")
        return "You connected"
    }

    @POST
    @Produces(ContentType.TEXT_PLAIN)
    @Accept(ContentType.TEXT_PLAIN)
    fun setSend(
        context: Context,
        requestInfo: RequestInfo,
        responseInfo: ResponseInfo,
        json: String
    ): String {
        println("Request: $json")
        return "Matric collected"
    }

    private fun getStackTrace(throwable: Throwable): String {
        val stringWriter = StringWriter()
        throwable.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }

    @ExceptionHandler
    @Produces(ContentType.TEXT_PLAIN)
    fun handleThrowable(throwable: Throwable, response: ResponseInfo) {
        println("Error: ${throwable.localizedMessage}")
        val throwableStr = getStackTrace(throwable)
        response.body = throwableStr.toByteArray()
    }
}