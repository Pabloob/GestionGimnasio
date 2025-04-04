package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class ClaseHorarioPostDTO {

    @NotNull
    private Long clase;

    @NotNull
    private DayOfWeek diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Long sala;

    @NotNull
    private Long instructor;
}