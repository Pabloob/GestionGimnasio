package com.gestiongimnasio.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trabajadores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Trabajador extends Usuario {

    @NotBlank
    private String direccion;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoTrabajador tipoTrabajador;

    public enum TipoTrabajador {
        ADMIN, INSTRUCTOR, RECEPCIONISTA
    }

    @Override
    protected void validacionesEspecificas() {
        if (getTipoUsuario() != TipoUsuario.TRABAJADOR) {
            throw new IllegalStateException("El tipo de usuario debe ser TRABAJADOR");
        }
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }
}