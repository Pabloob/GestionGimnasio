package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "trabajadores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Trabajador extends Usuario {

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotNull(message = "La hora de inicio no puede estar vacía")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin no puede estar vacía")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @NotNull(message = "El tipo de trabajador no puede estar vacío")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_trabajador", nullable = false)
    private TipoTrabajador tipoTrabajador;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Clase> clases;

    public enum TipoTrabajador {
        ADMIN, RECEPCIONISTA, INSTRUCTOR
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (getTipoUsuario() != Usuario.TipoUsuario.TRABAJADOR) {
            throw new IllegalStateException("El tipo de usuario debe ser TRABAJADOR");
        }
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }
}