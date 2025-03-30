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
public class UsuarioRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Email
    private String correo;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    @NotBlank
    private String contrasena;

    @NotNull
    @Past
    private LocalDate fechaDeNacimiento;

    @NotNull
    private TipoUsuario tipoUsuario;
}