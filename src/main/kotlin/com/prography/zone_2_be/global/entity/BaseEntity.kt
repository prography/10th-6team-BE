package com.prography.zone_2_be.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: Long = Instant.now().toEpochMilli()

    @LastModifiedDate
    @Column(nullable = false)
    var updateAt: Long = Instant.now().toEpochMilli()
}