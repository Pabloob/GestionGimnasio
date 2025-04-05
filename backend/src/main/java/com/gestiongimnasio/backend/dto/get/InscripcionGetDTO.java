package com.gestiongimnasio.backend.dto.get;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InscripcionGetDTO {
    private Long id;
    private ClienteGetDTO cliente;
    private ClaseGetDTO clase;
    private LocalDate fechaRegistro;
    private boolean asistio;
}