package com.blisspace.app.application.exception

abstract class ApplicationException(message: String) : RuntimeException(message) {

  override fun fillInStackTrace(): Throwable {
    return this
  }
}