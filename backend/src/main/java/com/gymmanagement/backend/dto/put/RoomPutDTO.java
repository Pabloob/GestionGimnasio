package com.gymmanagement.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomPutDTO {
    @NotBlank
    @Size(max = 100)
    private String name;
}
