package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Trabajador;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TrabajadorGetDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Trabajador.TipoTrabajador tipoTrabajador;
    private boolean activo;
    private List<Long> clasesIds;
}