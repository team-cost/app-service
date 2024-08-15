package com.blisspace.app.application.model

import com.blisspace.app.domain.Status

data class AppResponse (
  val id: String,
  val description: String,
  val status: Status,
  val icon: String,
  val version: String,
)