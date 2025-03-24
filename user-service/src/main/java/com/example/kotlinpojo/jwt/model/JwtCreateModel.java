package com.example.kotlinpojo.jwt.model;

import java.util.UUID;

public record JwtCreateModel(UUID userId, String userName, String mail, String role) {
}
