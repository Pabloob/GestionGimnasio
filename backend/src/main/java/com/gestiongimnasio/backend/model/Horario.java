package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@ToString(exclude = {"clase", "sala", "instructor"})
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;

    @NotNull
    private DayOfWeek diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private LocalDate fechasInicio;

    @NotNull
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Trabajador instructor;

    @PrePersist
    @PreUpdate
    private void validar() {
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }

}