package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClasePostDTO {

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Positive
    private Integer capacidadMaxima;

    @NotNull
    @Positive
    private Double precio;

    @NotBlank
    private String descripcion;

    private boolean activa = true;

}