package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerPostDTO {

    @NotNull
    private UserPostDTO user;

}
