package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClasePutDTO {
    @Size(max = 100)
    private String nombre;
    @Positive
    private Integer capacidadMaxima;
    @Positive
    private Double precio;
    private String descripcion;
    private boolean activa = true;
}