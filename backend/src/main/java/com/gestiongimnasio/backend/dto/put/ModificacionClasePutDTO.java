package com.gestiongimnasio.backend.dto.post;

import com.gestiongimnasio.backend.model.Clase.Dia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class ModificacionClasePostDTO {
    @NotNull
    private Long claseId;

    @NotNull @Positive
    private Double precio;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Long instructorId;

    @NotNull @Positive
    private Integer capacidadMaxima;

    @NotBlank @Size(max = 100)
    private String sala;

    @NotNull
    private Set<Dia> dias;

    @Size(max = 100)
    private String comentario;

    @NotNull
    private Long modificadoPor;
}