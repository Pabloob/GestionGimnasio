package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPutDTO {
    @Size(max = 100)
    private String nombre;

    private String contrasena;

    @Email
    private String correo;

    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    private boolean activo = true;
}