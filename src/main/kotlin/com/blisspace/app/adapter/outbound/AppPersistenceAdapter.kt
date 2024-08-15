package com.blisspace.app.adapter.outbound

import com.blisspace.app.adapter.outbound.mapper.PersistenceMapper
import com.blisspace.app.domain.App
import com.blisspace.app.infrastructure.mysql.AppRepository
import com.blisspace.app.port.outbound.LoadAppPort
import com.blisspace.app.port.outbound.LoadAllAppsPort
import com.blisspace.app.port.outbound.SaveAppPort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AppPersistenceAdapter(
    private val appRepository: AppRepository,
    private val persistenceMapper: PersistenceMapper,
) : LoadAllAppsPort,
    LoadAppPort,
    SaveAppPort {

  override fun loadAllApps(): List<App> {
    return appRepository
      .findAll()
      .map(persistenceMapper::toDomain)
  }

  override fun loadApp(id: String): App? {
    return appRepository
      .findById(id)
      .map(persistenceMapper::toDomain)
      .getOrNull()
  }

  override fun saveApp(app: App) {
    appRepository.save(persistenceMapper.toPersistence(app))
  }
}