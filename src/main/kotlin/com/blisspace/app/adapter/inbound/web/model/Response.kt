package com.blisspace.app.adapter.inbound.web.model

interface Response <T : Any?> {

  val statusCode: Int
  val code: String?
  val message: String
  val data: T
}