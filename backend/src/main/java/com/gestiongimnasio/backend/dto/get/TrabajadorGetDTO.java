package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Trabajador;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TrabajadorGetDTO {
    private UsuarioGetDTO usuario;
    private String direccion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Trabajador.TipoTrabajador tipoTrabajador;
}