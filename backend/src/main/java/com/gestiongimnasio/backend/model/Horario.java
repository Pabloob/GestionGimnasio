package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
public class ClaseHorario {

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

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Trabajador instructor;

    public void setSala(Sala sala) {
        this.sala = sala;
        sala.getHorarios().add(this);
    }

    public void setInstructor(Trabajador instructor) {
        this.instructor = instructor;
        instructor.getClasesHorario().add(this);
    }

    @PrePersist
    @PreUpdate
    private void validar() {
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }
}