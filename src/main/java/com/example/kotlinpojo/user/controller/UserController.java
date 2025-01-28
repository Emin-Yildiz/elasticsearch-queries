package com.example.kotlinpojo.user.controller;

import com.example.kotlinpojo.domain.response.Response;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.request.UserUpdateRequestModel;
import com.example.kotlinpojo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "User List Endpoint")
    public Response<Object> getUserList() {
        return new Response<>("User Records Retrieved Success", userService.getUserList());
    }

    @PutMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "User Update Endpoint")
    public Response<Object> updateUser(@RequestBody UserUpdateRequestModel userUpdateRequestModel, @PathVariable UUID userId) {
        return new Response<>("User Save Success",userService.updateUser(userUpdateRequestModel, userId));
    }

    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "User Delete Endpoint")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

}
