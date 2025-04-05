package com.gestiongimnasio.backend.dto.get;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioGetDTO {
    private Long id;
    private ClaseGetDTO clase;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private SalaGetDTO sala;
    private TrabajadorGetDTO instructor;
    private LocalDate fechasInicio;
    private LocalDate fechaFin;
}