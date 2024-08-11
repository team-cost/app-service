package com.blisspace.app.application

import com.blisspace.app.application.exception.AppAlreadyExistsException
import com.blisspace.app.application.exception.AppNotFoundException
import com.blisspace.app.application.mapper.ApplicationMapper
import com.blisspace.app.application.model.AppResponse
import com.blisspace.app.application.model.CreateAppRequest
import com.blisspace.app.domain.App
import com.blisspace.app.domain.Status
import com.blisspace.app.port.inbound.CreateAppUseCase
import com.blisspace.app.port.inbound.GetAppUseCase
import com.blisspace.app.port.inbound.GetAppsUseCase
import com.blisspace.app.port.inbound.UpdateAppStatusUseCase
import com.blisspace.app.port.outbound.LoadAppPort
import com.blisspace.app.port.outbound.LoadAppsPort
import com.blisspace.app.port.outbound.SaveAppPort
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class AppService(
    private val loadAppPort: LoadAppPort,
    private val loadAppsPort: LoadAppsPort,
    private val saveAppPort: SaveAppPort,
    private val applicationMapper: ApplicationMapper,
) : GetAppsUseCase,
    GetAppUseCase,
    CreateAppUseCase,
    UpdateAppStatusUseCase {

  override fun getApps(): List<AppResponse> {
    return loadAppsPort
      .loadApps()
      .map(applicationMapper::toResponse)
  }

  override fun getApp(id: String): AppResponse {
    return loadAppOrThrow(id).let(applicationMapper::toResponse)
  }

  override fun createApp(request: CreateAppRequest) {
    saveAppPort.saveApp(
      App.create(request.id, request.description, Status.PENDING, request.icon, request.version)
    )
  }

  override fun updateStatus(id: String, status: Status) {
    try {
      saveAppPort.saveApp(loadAppOrThrow(id).updateStatus(status))
    } catch (e: DuplicateKeyException) {
      throw AppAlreadyExistsException(id)
    }
  }

  private fun loadAppOrThrow(id: String): App {
    return loadAppPort.loadApp(id) ?: throw AppNotFoundException(id)
  }
}