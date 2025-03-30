package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Trabajador;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrabajadorPutDTO {
    @NotNull
    Long id;

    @Size(max = 100)
    private String nombre;
    private String contrasena;
    @Email
    private String correo;
    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;
    @Past
    private LocalDate fechaNacimiento;
    private String direccion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Trabajador.TipoTrabajador tipoTrabajador;
    private boolean activo;
}