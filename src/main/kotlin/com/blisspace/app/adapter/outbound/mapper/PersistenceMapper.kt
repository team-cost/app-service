package com.blisspace.app.adapter.outbound.mapper

import com.blisspace.app.domain.App
import com.blisspace.app.infrastructure.mysql.AppEntity
import org.springframework.stereotype.Component

@Component
class PersistenceMapper {

  fun toDomain(entity: AppEntity) = App.of(
    id = entity.id,
    description = entity.description,
    status = entity.status,
    icon = entity.icon,
    version = entity.version
  )

  fun toPersistence(domain: App) = AppEntity(
    id = domain.id,
    description = domain.description,
    status = domain.status,
    icon = domain.icon,
    version = domain.version
  )
}