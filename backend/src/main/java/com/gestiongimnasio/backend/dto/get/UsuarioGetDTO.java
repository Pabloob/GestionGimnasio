package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Usuario;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioGetDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private Usuario.TipoUsuario tipoUsuario;
    private boolean activo;
}