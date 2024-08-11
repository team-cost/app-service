package com.blisspace.app.infrastructure.mysql

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppRepository: JpaRepository<AppEntity, String> {
}