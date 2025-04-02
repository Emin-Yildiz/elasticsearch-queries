package com.example.kotlinpojo.role;

import com.example.kotlinpojo.domain.audit.Audit;
import com.example.kotlinpojo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "roles")
@Entity
public class Role extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @Version
    private Long version = 0L;

    public Role() {
    }

    private Role(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.users = builder.users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private Set<User> users = new HashSet<>();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder users(Set<User> users) {
            this.users = users;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
