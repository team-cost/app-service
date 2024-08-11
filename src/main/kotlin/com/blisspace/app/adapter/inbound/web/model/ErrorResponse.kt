package com.blisspace.app.adapter.inbound.web.model

import org.springframework.http.HttpStatus

class ErrorResponse(
    override val statusCode: Int,
    override val code: String? = null,
    override val message: String,
    override val data: Nothing?
) : Response<Nothing?> {

  companion object {
    fun error(status: HttpStatus? = null, message: String) = ErrorResponse(
      statusCode = status?.value() ?: HttpStatus.INTERNAL_SERVER_ERROR.value(),
      code = null,
      message = message,
      data = null
    )
  }
}