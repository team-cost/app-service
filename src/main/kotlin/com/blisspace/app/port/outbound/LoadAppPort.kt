package com.blisspace.app.port.outbound

import com.blisspace.app.domain.App

interface LoadAppPort {

  fun loadApp(id: String): App?
}