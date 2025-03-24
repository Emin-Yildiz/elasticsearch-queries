package com.example.kotlinpojo.user.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserSaveRequestModel(
        @NotBlank(message = "Mail can not be blank")
        String mail,
        @NotBlank(message = "Username can not be blank")
        String userName,
        @NotBlank(message = "Password can not be blank")
        String password,
        UUID roleId) {
}
