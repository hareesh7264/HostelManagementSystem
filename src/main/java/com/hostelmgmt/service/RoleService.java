package com.hostelmgmt.service;

import com.hostelmgmt.dto.RoleDto;
import com.hostelmgmt.entity.Role;

import java.util.List;

public interface RoleService {

    Role createRole(RoleDto dto);
    Role updateRole(Long id, RoleDto dto);
    void deleteRole(Long id);
    List<Role> getAllRoles();
    Role getRole(Long id);
}

