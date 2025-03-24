package com.example.kotlinpojo.user.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserUpdateRequestModel(
        @NotBlank(message = "Mail can not be null")
        String mail,
        @NotBlank(message = "Username can not be null")
        String userName,
        UUID roleId,
        String password) {
}
