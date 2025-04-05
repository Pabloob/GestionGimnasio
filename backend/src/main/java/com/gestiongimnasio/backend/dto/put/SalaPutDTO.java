package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SalaPutDTO {
    @NotBlank
    @Size(max = 100)
    private String nombre;
}