package com.blisspace.app.port.outbound

import com.blisspace.app.domain.App

interface SaveAppPort {

  fun saveApp(app: App)
}