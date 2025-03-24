package com.example.kotlinpojo.auth.controller;

import com.example.kotlinpojo.auth.model.request.LoginRequestModel;
import com.example.kotlinpojo.auth.service.AuthService;
import com.example.kotlinpojo.domain.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login Endpoint")
    public Response<Object> login(@RequestBody LoginRequestModel loginRequestModel){
        return new Response<>("Login Success", authService.login(loginRequestModel));
    }

}
