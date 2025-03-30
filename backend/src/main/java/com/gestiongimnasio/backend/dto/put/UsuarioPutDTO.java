package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Usuario.TipoUsuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPutDTO {
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
    private TipoUsuario tipoUsuario;
    private boolean activo;
}