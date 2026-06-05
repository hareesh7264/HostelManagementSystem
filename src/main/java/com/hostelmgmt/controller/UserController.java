package com.hostelmgmt.controller;

import com.hostelmgmt.dto.UserDto;
import com.hostelmgmt.entity.User;
import com.hostelmgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping ("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto newUser = userService.registerUser(userDto);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping ("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping ("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping ("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

