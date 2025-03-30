package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Clase;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ModificacionClaseGetDTO {
    private Long id;
    private Long claseId;
    private String claseNombre;
    private Double precio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long instructorId;
    private String instructorNombre;
    private Integer totalInscritos;
    private Integer capacidadMaxima;
    private String sala;
    private Set<Clase.Dia> dias;
    private LocalDate fechaModificacion;
    private String comentario;
    private Long modificadoPorId;
    private String modificadoPorNombre;
}