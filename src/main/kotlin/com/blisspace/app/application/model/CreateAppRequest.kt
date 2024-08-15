package com.blisspace.app.application.model

data class CreateAppRequest (
  val id: String,
  val description: String,
  val icon: String,
  val version: String,
)
