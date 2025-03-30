package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientePostDTO {
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
}