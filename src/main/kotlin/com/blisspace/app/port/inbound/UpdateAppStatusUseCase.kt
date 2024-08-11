package com.blisspace.app.port.inbound

import com.blisspace.app.domain.Status

interface UpdateAppStatusUseCase {

  fun updateStatus(id: String, status: Status)
}