package com.blisspace.app.adapter.inbound.web.exception

import com.blisspace.app.adapter.inbound.web.model.ErrorResponse
import com.blisspace.app.application.exception.ApplicationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalWebException {

  private val logger = LoggerFactory.getLogger(GlobalWebException::class.java)

  @ExceptionHandler(ApplicationException::class)
  fun handleApplicationException(e: ApplicationException): ResponseEntity<ErrorResponse> {
    return ResponseEntity
      .badRequest()
      .body(ErrorResponse.error(message = e.localizedMessage))
  }

  @ExceptionHandler(Exception::class)
  fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
    logger.error(e.message)
    return ResponseEntity
      .internalServerError()
      .body(ErrorResponse.error(message = e.localizedMessage))
  }
}