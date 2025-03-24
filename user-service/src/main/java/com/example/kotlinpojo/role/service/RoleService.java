package com.example.kotlinpojo.role.service;

import com.example.kotlinpojo.domain.exception.exceptions.NotAvailableException;
import com.example.kotlinpojo.role.Role;
import com.example.kotlinpojo.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotAvailableException(String.format("Role Not Found. ID: %S",id)));
    }
}
