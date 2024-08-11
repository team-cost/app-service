package com.blisspace.app.application.mapper

import com.blisspace.app.application.model.AppResponse
import com.blisspace.app.domain.App
import org.springframework.stereotype.Component

@Component
class ApplicationMapper {

  fun toResponse(app: App) = AppResponse(
    id = app.id,
    description = app.description,
    status = app.status,
    icon = app.icon,
    version = app.version
  )
}