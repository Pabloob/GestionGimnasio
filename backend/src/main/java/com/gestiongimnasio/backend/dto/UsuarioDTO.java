package com.gestiongimnasio.backend.dto;

import com.gestiongimnasio.backend.model.Usuario.TipoUsuario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String correo;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaDeNacimiento;

    @NotNull(message = "La fecha de registro no puede estar vacía")
    @Past(message = "La fecha de registro debe ser en el pasado")
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El tipo de usuario no puede estar vacío")
    private TipoUsuario tipoUsuario;

    private boolean activo = true;
}