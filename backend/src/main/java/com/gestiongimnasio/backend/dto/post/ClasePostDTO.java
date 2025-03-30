package com.gestiongimnasio.backend.dto.post;

import com.gestiongimnasio.backend.model.Clase;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ClasePostDTO {
    @NotBlank @Size(max = 100)
    private String nombre;

    @NotNull @Positive
    private Double precio;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @NotNull
    private Long instructorId;

    @NotNull @Positive
    private Integer capacidadMaxima;

    @NotBlank @Size(max = 100)
    private String sala;

    @NotNull
    private Set<Clase.Dia> dias;
}