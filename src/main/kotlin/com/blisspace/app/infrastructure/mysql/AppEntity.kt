package com.blisspace.app.infrastructure.mysql

import com.blisspace.app.domain.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity(name = "app")
class AppEntity(
    id: String,
    description: String,
    status: Status,
    icon: String,
    version: String,
) {

  @Id
  var id: String = id
    private set
  var description: String = description
    protected set

  @Enumerated(EnumType.STRING)
  var status: Status = status
    protected set
  var icon: String = icon
    protected set
  var version: String = version
    protected set

  @CreatedDate
  @Column(nullable = false, updatable = false)
  var createdAt: LocalDateTime = LocalDateTime.now()
    protected set

  @LastModifiedDate
  @Column(nullable = false)
  var modifiedAt: LocalDateTime = LocalDateTime.now()
    protected set
}