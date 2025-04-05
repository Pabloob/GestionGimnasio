package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Trabajador;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TrabajadorPutDTO {
    UsuarioPutDTO usuarioPutDTO;

    private String direccion;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private Trabajador.TipoTrabajador tipoTrabajador;
}