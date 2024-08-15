package com.blisspace.app.adapter.inbound.web

import com.blisspace.app.adapter.inbound.web.model.DataResponse
import com.blisspace.app.adapter.inbound.web.model.ErrorResponse
import com.blisspace.app.application.exception.AppAlreadyExistsException
import com.blisspace.app.application.exception.AppNotFoundException
import com.blisspace.app.application.model.AppResponse
import com.blisspace.app.application.model.CreateAppRequest
import com.blisspace.app.port.inbound.CreateAppUseCase
import com.blisspace.app.port.inbound.GetAppUseCase
import com.blisspace.app.port.inbound.GetAppsUseCase
import com.blisspace.app.port.inbound.UpdateAppStatusUseCase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.beInstanceOf
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@WebMvcTest(controllers = [AppController::class])
@AutoConfigureMockMvc(addFilters = false)
class AppControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val getAppUseCase: GetAppUseCase,
    @MockkBean private val getAppsUseCase: GetAppsUseCase,
    @MockkBean private val createAppUseCase: CreateAppUseCase,
    @MockkBean private val updateAppStatusUseCase: UpdateAppStatusUseCase,
) : DescribeSpec({

  val objectMapper = jacksonObjectMapper()

  describe("getApps") {
    context("when there are no apps") {
      every { getAppsUseCase.getApps() } returns listOf()
      it("should return empty list") {
        val result = mockMvc
          .get("/api/v1.0/apps")
          .andExpect { status { isOk() } }
          .andReturn()
        objectMapper.readValue<DataResponse<List<AppResponse>>>(result.response.contentAsString).data shouldBe emptyList()
      }
    }

    context("when exists app") {
      val app = mockk<AppResponse>(relaxed = true)
      every { getAppsUseCase.getApps() } returns listOf(app)
      it("should return existing apps") {
        val result = mockMvc
          .get("/api/v1.0/apps")
          .andExpect { status { isOk() } }
          .andReturn()
        objectMapper.readValue<DataResponse<List<AppResponse>>>(result.response.contentAsString).data should beInstanceOf<List<AppResponse>>()
      }
    }
  }

  describe("getApp") {

    context("when there is no app") {
      every { getAppUseCase.getApp(any()) } answers { throw AppNotFoundException("APP") }
      it("should return error response") {
        mockMvc
          .get("/api/v1.0/apps/APP")
          .andExpect { status { isBadRequest() } }
      }
    }

    context("when exists app") {
      val app = mockk<AppResponse>(relaxed = true)
      every { getAppUseCase.getApp(any()) } returns app
      it("should return app response") {
        val result = mockMvc
          .get("/api/v1.0/apps/APP")
          .andExpect { status { isOk() } }
          .andReturn()
        objectMapper.readValue<DataResponse<AppResponse>>(result.response.contentAsString).data should beInstanceOf<AppResponse>()
      }
    }
  }

  describe("createApp") {
    val app = mockk<AppResponse>(relaxed = true)
    context("when there is no app") {
      justRun { createAppUseCase.createApp(any()) }
      every { getAppUseCase.getApp(any()) } returns app
      val request = mockk<CreateAppRequest>(relaxed = true)
      it("should save app and response") {
        val result = mockMvc
          .post("/api/v1.0/apps") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
          }
          .andExpect { status { isCreated() } }
          .andReturn()
        objectMapper.readValue<DataResponse<AppResponse>>(result.response.contentAsString).data should beInstanceOf<AppResponse>()
      }
    }

    context("when exists app") {
      val request = mockk<CreateAppRequest>(relaxed = true)
      every { createAppUseCase.createApp(any()) } answers { throw AppAlreadyExistsException("APP") }
      it("should return error response") {
        val result = mockMvc
          .post("/api/v1.0/apps") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
          }
          .andExpect { status { isBadRequest() } }
          .andReturn()
        objectMapper.readValue<ErrorResponse>(result.response.contentAsString).message shouldContain "App already exists"
      }
    }
  }

  describe("updateStatus") {
    context("when exists app") {
      val app = mockk<AppResponse>(relaxed = true)
      justRun { updateAppStatusUseCase.updateStatus(any(), any()) }
      every { getAppUseCase.getApp(any()) } returns app
      it("should update app status") {
        mockMvc.put("/api/v1.0/apps/APP/status/ACTIVE") {
          contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
      }
    }
  }
})
