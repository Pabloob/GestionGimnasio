package com.gestiongimnasio.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioLoginDTO {
    @NotBlank
    private String correo;

    @NotBlank
    private String contrasena;
}