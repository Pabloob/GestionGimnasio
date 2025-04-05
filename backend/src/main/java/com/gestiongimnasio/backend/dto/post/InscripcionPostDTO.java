package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InscripcionPostDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long claseId;

    private boolean asistio = false;
}