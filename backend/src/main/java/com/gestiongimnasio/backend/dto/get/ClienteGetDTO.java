package com.gestiongimnasio.backend.dto.get;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteGetDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private int inasistencias;
    private boolean activo;
    private List<Long> inscripcionesIds;
}