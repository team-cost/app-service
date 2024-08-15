package com.blisspace.app.port.outbound

import com.blisspace.app.domain.App

interface LoadAllAppsPort {

  fun loadAllApps() : List<App>
}