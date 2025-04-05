package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioPutDTO {
    private Long claseId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long salaId;
    private Long instructorId;
    private LocalDate fechasInicio;
    private LocalDate fechaFin;
}