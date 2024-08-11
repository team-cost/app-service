package com.blisspace.app.adapter.outbound

import com.blisspace.app.adapter.outbound.mapper.PersistenceMapper
import com.blisspace.app.domain.App
import com.blisspace.app.infrastructure.mysql.AppEntity
import com.blisspace.app.infrastructure.mysql.AppRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import io.mockk.every
import io.mockk.mockk
import java.util.*

class AppPersistenceAdapterTest : DescribeSpec({
  val appRepository: AppRepository = mockk(relaxed = true)
  val persistenceMapper = PersistenceMapper()
  val appPersistenceAdapter = AppPersistenceAdapter(appRepository, persistenceMapper)

  describe("loadApps") {
    val appEntity = mockk<AppEntity>(relaxed = true)
    every { appRepository.findAll() } returns listOf(appEntity)
    context("load app domains") {
      it("should return list") {
        shouldNotThrowAny { appPersistenceAdapter.loadApps() } should beInstanceOf<List<App>>()
      }
    }
  }

  describe("loadApp") {
    context("if app not found") {
      every { appRepository.findById(any()) } returns Optional.empty()
      it("should return null") {
        appPersistenceAdapter.loadApp("APP") shouldBe null
      }
    }

    context("if app exists") {
      val appEntity = mockk<AppEntity>(relaxed = true)
      every { appRepository.findById(any()) } returns Optional.of(appEntity)
      it("should return app domain") {
        val app = appPersistenceAdapter.loadApp("APP")
        app shouldNotBe null
      }
    }
  }

  describe("saveApp") {
    val app = mockk<App>(relaxed = true)

    context("if proper app is given") {
      every { appRepository.save(any()) } returns persistenceMapper.toPersistence(app)
      it("should save app") {
        shouldNotThrowAny { appPersistenceAdapter.saveApp(app) }
      }
    }
  }
})
