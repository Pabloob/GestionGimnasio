package com.gymmanagement.backend.dto.get;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gymmanagement.backend.model.User;

import lombok.Data;

@Data
public class UsuarioGetDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private User.TipoUsuario tipoUsuario;
    private boolean activo;
}