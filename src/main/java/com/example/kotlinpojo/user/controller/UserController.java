package com.example.kotlinpojo.user.controller;

import com.example.kotlinpojo.domain.response.Response;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "User Save Endpoint")
    public Response<Object> saveUser(@RequestBody UserSaveRequestModel userSaveRequestModel) {
        return new Response<>("User Save Success",userService.saveUser(userSaveRequestModel));
    }

}
