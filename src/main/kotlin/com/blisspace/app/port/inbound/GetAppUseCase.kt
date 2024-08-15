package com.blisspace.app.port.inbound

import com.blisspace.app.application.model.AppResponse

interface GetAppUseCase {

  fun getApp(id: String) : AppResponse
}