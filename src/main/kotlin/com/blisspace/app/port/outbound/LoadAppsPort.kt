package com.blisspace.app.port.outbound

import com.blisspace.app.domain.App

interface LoadAppsPort {

  fun loadApps() : List<App>
}