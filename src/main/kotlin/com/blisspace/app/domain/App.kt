package com.blisspace.app.domain

class App private constructor(
    val id: String,
    var description: String,
    var status: Status,
    var icon: String,
    var version: String,
) {

  fun updateDescription(description: String) {
    this.description = description
  }

  fun updateStatus(status: Status): App {
    if (this.status != status) {
      this.status = status
    }
    return this
  }

  fun updateIcon(icon: String) {
    this.icon = icon
  }

  fun updateVersion(version: String) {
    this.version = version
  }

  companion object {

    fun create(id: String, description: String, status: Status, icon: String, version: String) =
      App(id = id, description = description, status = status, icon = icon, version = version)

    fun of(id: String, description: String, status: Status, icon: String, version: String) =
      App(id = id, description = description, status = status, icon = icon, version = version)
  }
}