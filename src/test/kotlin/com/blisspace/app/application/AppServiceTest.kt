package com.blisspace.app.application

import com.blisspace.app.application.exception.AppAlreadyExistsException
import com.blisspace.app.application.exception.AppNotFoundException
import com.blisspace.app.application.mapper.ApplicationMapper
import com.blisspace.app.application.model.CreateAppRequest
import com.blisspace.app.domain.App
import com.blisspace.app.domain.Status
import com.blisspace.app.port.outbound.LoadAppPort
import com.blisspace.app.port.outbound.LoadAllAppsPort
import com.blisspace.app.port.outbound.SaveAppPort
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.springframework.dao.DuplicateKeyException

class AppServiceTest : DescribeSpec({
  val loadAppPort: LoadAppPort = mockk(relaxed = true)
  val loadAllAppsPort: LoadAllAppsPort = mockk(relaxed = true)
  val saveAppPort: SaveAppPort = mockk(relaxed = true)
  val applicationMapper = ApplicationMapper()
  val appService = AppService(loadAppPort, loadAllAppsPort, saveAppPort, applicationMapper)

  describe("getApps") {

    context("when there are no apps") {
      every { loadAllAppsPort.loadAllApps() } returns listOf()
      it("should return empty list") {
        appService.getApps() shouldBe emptyList()
      }
    }

    context("when exists apps") {
      val app = mockk<App>(relaxed = true)
      every { loadAllAppsPort.loadAllApps() } returns listOf(app)
      it("should success") {
        appService.getApps() shouldBe listOf(applicationMapper.toResponse(app))
      }
    }
  }

  describe("getApp") {
    val app = mockk<App>(relaxed = true)

    context("when exists app") {
      every { loadAppPort.loadApp(any()) } returns app
      it("should success") {
        shouldNotThrowAny { appService.getApp("APP") }
      }
    }

    context("when not exists") {
      every { loadAppPort.loadApp(any()) } returns null
      it("throws AppNotFoundException") {
        shouldThrow<AppNotFoundException> { appService.getApp("APP") }
      }
    }
  }

  describe("createApp") {
    val request = mockk<CreateAppRequest>(relaxed = true)
    context("when already exists app") {
      every { saveAppPort.saveApp(any()) } answers { throw DuplicateKeyException("Duplicate Key") }
      it("throws AppAlreadyExistsException") {
        shouldThrow<AppAlreadyExistsException> { appService.createApp(request) }
      }
    }

    context("when not exists") {
      justRun { saveAppPort.saveApp(any()) }
      it("should succeed") {
        shouldNotThrowAny { appService.createApp(request) }
      }
    }
  }

  describe("updateStatus") {

    context("when app not exists") {
      every { loadAppPort.loadApp(any()) } returns null
      it("should throw AppNotFoundException") {
        shouldThrow<AppNotFoundException> { appService.updateStatus("APP", Status.ACTIVE) }
      }
    }

    context("when app exists") {
      val app = mockk<App>(relaxed = true)
      every { loadAppPort.loadApp(any()) } returns app
      justRun { saveAppPort.saveApp(any()) }
      it("should success") {
        shouldNotThrowAny { appService.updateStatus("APP", Status.ACTIVE) }
      }
    }
  }
})
