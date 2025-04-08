package com.gymmanagement.backend.dto.get;

import lombok.Data;

@Data
public class ClaseGetDTO {
    private Long id;
    private String nombre;
    private Integer capacidadMaxima;
    private Double precio;
    private String descripcion;
    private boolean activa;
}