package com.hostelmgmt.controller;

import com.hostelmgmt.dto.RoleDto;
import com.hostelmgmt.entity.Role;
import com.hostelmgmt.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role create(@RequestBody RoleDto dto) {
        return roleService.createRole(dto);
    }

    @PutMapping ("/{id}")
    public Role update(@PathVariable Long id, @RequestBody RoleDto dto) {
        return roleService.updateRole(id, dto);
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping ("/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @DeleteMapping ("/{id}")
    public String delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "Role deleted successfully";
    }
}

