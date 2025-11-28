package com.hostelmgmt.service;

import com.hostelmgmt.dto.UserDto;
import com.hostelmgmt.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(UserDto dto);
    User updateUser(Long userId, UserDto dto);
    void deleteUser(Long id);
    List<User> getAllUsers();
    User getUser(Long id);


}

