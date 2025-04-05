package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SalaPostDTO {
    @NotBlank
    @Size(max = 100)
    private String nombre;
}