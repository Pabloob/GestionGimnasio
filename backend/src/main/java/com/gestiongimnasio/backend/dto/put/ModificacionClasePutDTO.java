package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Clase.Dia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ModificacionClasePutDTO {
    @NotNull
    private Long id;

    @Positive
    private Double precio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long instructorId;
    @Positive
    private Integer capacidadMaxima;
    @Size(max = 100)
    private String sala;
    private Set<Dia> dias;
    @Size(max = 100)
    private String comentario;
    private Long modificadoPor;
}