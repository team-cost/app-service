package com.blisspace.app.adapter.outbound

import com.blisspace.app.domain.App
import com.blisspace.app.port.outbound.LoadAppPort
import com.blisspace.app.port.outbound.LoadAppsPort
import com.blisspace.app.port.outbound.SaveAppPort
import org.springframework.stereotype.Service

@Service
class AppPersistenceAdapter : LoadAppsPort,
                              LoadAppPort,
                              SaveAppPort {

  override fun loadApps(): List<App> {
    TODO("Not yet implemented")
  }

  override fun loadApp(id: String): App? {
    TODO("Not yet implemented")
  }

  override fun saveApp(app: App) {
    TODO("Not yet implemented")
  }
}