package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioLoginDTO {
    @NotBlank @Email
    private String correo;

    @NotBlank
    private String contrasena;
}