package com.example.kotlinpojo.role.controller;

import com.example.kotlinpojo.domain.response.Response;
import com.example.kotlinpojo.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get All Role Endpoint")
    public Response<Object> getAllRoles() {
        return new Response<>("Get All Roles Success",roleService.getAllRoles());
    }
}
