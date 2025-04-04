package com.gestiongimnasio.backend.dto.put;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class ClaseHorarioPutDTO {
    private Long clase;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long sala;
    private Long instructor;
}