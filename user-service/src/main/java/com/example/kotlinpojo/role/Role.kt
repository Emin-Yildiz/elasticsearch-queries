package com.example.kotlinpojo.role

import com.example.kotlinpojo.domain.audit.Audit
import com.example.kotlinpojo.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Table(name = "roles")
@Entity
data class Role @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val name: String,

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    val users: Set<User> = emptySet(),

    @Embedded
    val audit: Audit = Audit()
): Serializable {
    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private lateinit var roleName: String
        private var id: UUID = UUID.randomUUID()
        private var users: Set<User> = emptySet()
        private var audit: Audit = Audit()

        fun id(id: UUID) = apply { this.id = id }
        fun name(name: String) = apply { this.roleName = name }
        fun users(users: Set<User>) = apply { this.users = users }
        fun audit(audit: Audit) = apply { this.audit = audit }

        fun build(): Role {
            require(::roleName.isInitialized) { "Role 'name' must be initialized" }
            return Role(
                id = id,
                name = roleName, // roleName'i Role'a atıyoruz
                users = users,
                audit = audit
            )
        }
    }
}
