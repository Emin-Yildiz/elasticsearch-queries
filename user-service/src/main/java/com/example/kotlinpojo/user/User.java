package com.example.kotlinpojo.user;

import com.example.kotlinpojo.domain.audit.Audit;
import com.example.kotlinpojo.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "users")
@Entity
public class User extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String mail;
    private String username;
    @JsonIgnore
    private String password;

    @ManyToOne
    private Role role;

    @Version
    private Long version = 0L;

    public User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.mail = builder.mail;
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
    }

    public User(String mail, String admin, String password, Role adminRole) {
        this.mail = mail;
        this.username = admin;
        this.password = password;
        this.role = adminRole;
    }

    public User(UUID uuid, String mail, String admin, String password, Role adminRole) {
        this.id = uuid;
        this.mail = mail;
        this.username = admin;
        this.password = password;
        this.role = adminRole;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static class Builder {
        private UUID id;
        private String mail;
        private String username;
        private String password;
        private Role role;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder mail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

