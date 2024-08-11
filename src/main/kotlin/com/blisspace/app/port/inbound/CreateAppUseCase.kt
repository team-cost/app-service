package com.blisspace.app.port.inbound

import com.blisspace.app.application.model.CreateAppRequest

interface CreateAppUseCase {

  fun createApp(request: CreateAppRequest)
}