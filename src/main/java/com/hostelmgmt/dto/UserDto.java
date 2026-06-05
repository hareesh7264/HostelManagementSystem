package com.hostelmgmt.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.PriorityQueue;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Long roleId;
    private String roleName;
    private LocalDateTime createdAt;
    private Boolean isActive;

}

