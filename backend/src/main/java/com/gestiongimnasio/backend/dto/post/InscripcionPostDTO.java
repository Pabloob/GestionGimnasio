package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class InscripcionPostDTO {
    @NotNull
    private Long clienteId;

    @NotNull
    private Long claseId;

    private Long pagoId;
}