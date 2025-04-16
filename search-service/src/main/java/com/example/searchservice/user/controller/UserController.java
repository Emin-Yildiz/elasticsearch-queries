package com.example.searchservice.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import com.example.searchservice.domain.response.Response;
import com.example.searchservice.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/search/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get User By Id Controller")
    public Response<Object> getUserById(@PathVariable(name = "userId") UUID userID){
        return new Response<>("User Record Retrieved Success",userService.getUserById(userID));
    }
}
