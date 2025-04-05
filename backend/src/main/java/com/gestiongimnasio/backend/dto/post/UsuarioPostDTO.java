package com.gestiongimnasio.backend.dto.post;

import com.gestiongimnasio.backend.model.Usuario.TipoUsuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPostDTO {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    private String contrasena;

    @NotBlank
    @Email
    private String correo;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    @NotNull
    @Past
    private LocalDate fechaNacimiento;

    @NotNull
    private TipoUsuario tipoUsuario;

    private boolean activo = true;
}