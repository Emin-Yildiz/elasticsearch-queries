package com.example.kotlinpojo.user

import com.example.kotlinpojo.domain.audit.Audit
import com.example.kotlinpojo.role.Role
import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID

@Table(name = "users")
@Entity
data class User @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val mail: String,
    val username: String,
    val password: String,

    @ManyToOne
    val role: Role,

    @Embedded
    val audit: Audit = Audit()
): Serializable
