package com.gestiongimnasio.backend.dto.post;

import com.gestiongimnasio.backend.model.Trabajador;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TrabajadorPostDTO {

    @NotNull
    UsuarioPostDTO usuarioPostDTO;

    @NotBlank
    private String direccion;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Trabajador.TipoTrabajador tipoTrabajador;
}