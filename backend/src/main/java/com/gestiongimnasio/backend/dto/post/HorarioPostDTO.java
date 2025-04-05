package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioPostDTO {

    @NotNull
    private Long claseId;

    @NotNull
    private DayOfWeek diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Long salaId;

    @NotNull
    private Long instructorId;

    @NotNull
    private LocalDate fechasInicio;

    @NotNull
    private LocalDate fechaFin;
}