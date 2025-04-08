package com.gymmanagement.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomPostDTO {

    @NotBlank
    @Size(max = 100)
    private String name;
}
