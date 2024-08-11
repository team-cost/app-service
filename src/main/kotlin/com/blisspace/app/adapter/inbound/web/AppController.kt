package com.blisspace.app.adapter.inbound.web

import com.blisspace.app.adapter.inbound.web.model.DataResponse
import com.blisspace.app.application.model.AppResponse
import com.blisspace.app.application.model.CreateAppRequest
import com.blisspace.app.domain.Status
import com.blisspace.app.port.inbound.CreateAppUseCase
import com.blisspace.app.port.inbound.GetAppUseCase
import com.blisspace.app.port.inbound.GetAppsUseCase
import com.blisspace.app.port.inbound.UpdateAppStatusUseCase
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AppController(
    private val getAppUseCase: GetAppUseCase,
    private val getAppsUseCase: GetAppsUseCase,
    private val createAppUseCase: CreateAppUseCase,
    private val updateAppStatusUseCase: UpdateAppStatusUseCase,
) {

  @GetMapping("/api/v1.0/apps")
  fun getApps(): ResponseEntity<DataResponse<List<AppResponse>>> {
    return ResponseEntity
      .ok()
      .body(DataResponse.success(data = getAppsUseCase.getApps()))
  }

  @GetMapping("/api/v1.0/apps/{id}")
  fun getApp(@PathVariable("id") id: String): ResponseEntity<DataResponse<AppResponse>> {
    return ResponseEntity
      .ok()
      .body(DataResponse.success(data = getAppUseCase.getApp(id)))
  }

  @PostMapping("/api/v1.0/apps")
  fun createApp(@RequestBody request: CreateAppRequest): ResponseEntity<DataResponse<AppResponse>> {
    createAppUseCase.createApp(request)
    val app = getAppUseCase.getApp(request.id)
    return ResponseEntity
      .created(URI.create("/api/v1.0/apps/${app.id}"))
      .body(DataResponse.success(data = app))
  }

  @PutMapping("/api/v1.0/apps/{id}/status")
  fun updateStatus(
      @PathVariable("id") id: String,
      @PathParam("status") status: Status,
  ): ResponseEntity<DataResponse<AppResponse>> {
    updateAppStatusUseCase.updateStatus(id, status)
    return ResponseEntity
      .ok()
      .body(DataResponse.success(data = getAppUseCase.getApp(id)))
  }
}