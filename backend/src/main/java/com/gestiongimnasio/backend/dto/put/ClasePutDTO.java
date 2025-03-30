package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Clase;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ClasePutDTO {
    @NotNull
    Long id;
    @Size(max = 100)
    private String nombre;
    @Positive
    private Double precio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long instructorId;
    @Positive
    private Integer capacidadMaxima;
    @Size(max = 100)
    private String sala;
    private Set<Clase.Dia> dias;
    private boolean activa;
}