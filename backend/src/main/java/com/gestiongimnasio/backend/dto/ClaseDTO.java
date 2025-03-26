package com.gestiongimnasio.backend.dto;

import com.gestiongimnasio.backend.model.Clase.Dia;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio no puede estar vacío")
    @Positive(message = "El precio debe ser un valor positivo")
    private Double precio;

    @NotNull(message = "La hora de inicio no puede estar vacía")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin no puede estar vacía")
    private LocalTime horaFin;

    @NotNull(message = "El instructor no puede estar vacío")
    private Long idInstructor;

    @NotNull(message = "La capacidad máxima no puede estar vacía")
    @Positive(message = "La capacidad máxima debe ser un valor positivo")
    private Integer capacidadMaxima;

    @NotBlank(message = "La sala no puede estar vacía")
    @Size(max = 100, message = "La sala no puede exceder los 100 caracteres")
    private String sala;

    @NotNull(message = "Los días no pueden estar vacíos")
    @Size(min = 1, message = "Debe haber al menos un día seleccionado")
    private Set<Dia> dias;

    private boolean activa = true;
}