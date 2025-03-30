package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InscripcionPutDTO {
    @NotNull
    Long id;
    private Long clienteId;
    private Long claseId;
    private Long pagoId;
    private Boolean estadoPago;
}