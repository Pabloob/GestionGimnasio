package com.gestiongimnasio.backend.dto;

import com.gestiongimnasio.backend.model.Trabajador.TipoTrabajador;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TrabajadorDTO extends UsuarioDTO {

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    private String direccion;

    @NotNull(message = "La hora de inicio no puede estar vacía")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin no puede estar vacía")
    private LocalTime horaFin;

    @NotNull(message = "El tipo de trabajador no puede estar vacío")
    private TipoTrabajador tipoTrabajador;

}