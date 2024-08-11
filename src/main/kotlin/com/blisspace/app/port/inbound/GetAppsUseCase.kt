package com.blisspace.app.port.inbound

import com.blisspace.app.application.model.AppResponse

interface GetAppsUseCase {

  fun getApps() : List<AppResponse>
}