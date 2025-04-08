package com.gymmanagement.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPutDTO {

    @Size(max = 100)
    private String name;

    private String password;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;

    private boolean active = true;
}
