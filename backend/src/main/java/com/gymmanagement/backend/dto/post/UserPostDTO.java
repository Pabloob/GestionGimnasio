package com.gymmanagement.backend.dto.post;

import com.gymmanagement.backend.model.User.UserType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private UserType userType;

    private boolean active = true;
}
