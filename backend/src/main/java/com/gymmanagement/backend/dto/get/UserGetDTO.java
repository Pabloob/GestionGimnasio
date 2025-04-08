package com.gymmanagement.backend.dto.get;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gymmanagement.backend.model.User;

import lombok.Data;

@Data
public class UserGetDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime registrationDate;
    private User.UserType userType;
    private boolean active;
}
