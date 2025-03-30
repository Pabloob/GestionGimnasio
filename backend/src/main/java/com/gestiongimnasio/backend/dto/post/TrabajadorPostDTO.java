package com.gestiongimnasio.backend.dto.post;

import com.gestiongimnasio.backend.model.Trabajador;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrabajadorPostDTO {
    @NotBlank @Size(max = 100)
    private String nombre;

    @NotBlank
    private String contrasena;

    @NotBlank @Email
    private String correo;

    @NotBlank @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    @NotNull @Past
    private LocalDate fechaNacimiento;

    @NotBlank
    private String direccion;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Trabajador.TipoTrabajador tipoTrabajador;
}