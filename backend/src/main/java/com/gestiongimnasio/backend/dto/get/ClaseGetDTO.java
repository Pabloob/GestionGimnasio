package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Clase;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ClaseGetDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long instructorId;
    private String instructorNombre;
    private Integer totalInscritos;
    private Integer capacidadMaxima;
    private String sala;
    private Set<Clase.Dia> dias;
    private boolean activa;
}