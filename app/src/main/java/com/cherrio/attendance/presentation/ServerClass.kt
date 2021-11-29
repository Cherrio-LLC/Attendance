package com.cherrio.attendance.presentation

import ru.skornei.restserver.annotations.RestServer
import ru.skornei.restserver.server.BaseRestServer

/**
 *Created by Ayodele on 11/25/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@RestServer(
    port = ServerClass.PORT,
    controllers = [AttendanceApi::class]
)
class ServerClass: BaseRestServer() {
    companion object{
        const val PORT = 8080
    }
}