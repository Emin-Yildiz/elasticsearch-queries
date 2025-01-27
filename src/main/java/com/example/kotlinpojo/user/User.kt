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
    val role: Role? = null,

    @Embedded
    val audit: Audit = Audit()
): Serializable {
    class UserBuilder {
        private var id: UUID = UUID.randomUUID()
        private var mail: String = ""
        private var username: String = ""
        private var password: String = ""
        private var role: Role? = null
        private var audit: Audit = Audit()

        fun id(id: UUID) = apply { this.id = id }
        fun mail(mail: String) = apply { this.mail = mail }
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun role(role: Role) = apply { this.role = role }
        fun audit(audit: Audit) = apply { this.audit = audit }

        fun build(): User {
            return User(
                id = id,
                mail = mail,
                username = username,
                password = password,
                role = role,
                audit = audit
            )
        }
    }
}


