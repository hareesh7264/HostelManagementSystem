package com.hostelmgmt.service.impl;

import com.hostelmgmt.dto.RoleDto;
import com.hostelmgmt.entity.Role;
import com.hostelmgmt.exception.*;
import com.hostelmgmt.repository.RoleRepository;
import com.hostelmgmt.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired private RoleRepository roleRepo;

    @Override
    public Role createRole(RoleDto dto) {

        if (roleRepo.existsByRoleName(dto.getRoleName())) {
            throw new RoleAlreadyExistsException("Role already exists");
        }

        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        role.setIsActive((true));
        role.setCreatedAt(LocalDate.now());
        return roleRepo.save(role);
    }

    @Override
    public Role updateRole(Long id, RoleDto dto) {
        Role role = getRole(id);
        role.setRoleName(dto.getRoleName());
        role.setIsActive(dto.getActive());
        role.setUpdatedAt(LocalDate.now());
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = getRole(id);
        roleRepo.delete(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRole(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }
}

