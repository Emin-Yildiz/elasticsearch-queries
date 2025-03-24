package com.example.kotlinpojo.domain.audit

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.util.*

@Embeddable
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
data class Audit @JvmOverloads constructor(

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val creationDate: Date = Date(),

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    val lastModifiedDate: Date = Date(),

    @Column(name = "is_active")
    val isActive: Boolean = true,
)
