package com.blisspace.app.adapter.inbound.web.model

import org.springframework.http.HttpStatus

class DataResponse<T>(
    override val statusCode: Int,
    override val code: String? = null,
    override val message: String,
    override val data: T,
) : Response<T> {

  companion object {
    fun <T> success(status: HttpStatus? = null, data: T): DataResponse<T> {
      return DataResponse(
        statusCode = status?.value() ?: HttpStatus.OK.value(),
        code = null,
        message = "Success",
        data = data
      )
    }
  }
}