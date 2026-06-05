package com.hostelmgmt.service.impl;

import com.hostelmgmt.dto.UserDto;
import com.hostelmgmt.entity.Role;
import com.hostelmgmt.entity.User;
import com.hostelmgmt.exception.RoleNotFoundException;
import com.hostelmgmt.exception.UserNotFoundException;
import com.hostelmgmt.repository.RoleRepository;
import com.hostelmgmt.repository.UserRepository;
import com.hostelmgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("This email is already registered");
        }
       Role role = roleRepository.findById(userDto.getRoleId())
               .orElseThrow(()-> new RuntimeException("Role not found"));
        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(role);
        user.setActive(true);

        return mapToDto(userRepository.save(user));
    }

    private UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setRoleId(user.getRole().getRoleId());
        dto.setRoleName(user.getRole().getRoleName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setIsActive(user.getActive());
        return dto;
    }

    @Override
    public User updateUser(Long userId, UserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
         if(userRepository.existsByEmail(dto.getEmail()))
             throw new RuntimeException("Email already Existed");

        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(role);
        user.setActive(dto.getIsActive());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found Exception"));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUser(Long id) {
       User user = userRepository.findById(id)
               .orElseThrow(()-> new UserNotFoundException("User Not found"));

       return mapToDto(user);
    }
}

