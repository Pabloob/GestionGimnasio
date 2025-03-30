package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientePutDTO {
    @NotNull
    Long id;

    @Size(max = 100)
    private String nombre;
    private String contrasena;

    @Email
    private String correo;

    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    @PositiveOrZero
    private int inasistencias;

    private boolean activo;
}