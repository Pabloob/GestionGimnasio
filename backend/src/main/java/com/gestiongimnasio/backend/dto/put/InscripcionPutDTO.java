package com.gestiongimnasio.backend.dto.put;

import lombok.Data;

@Data
public class InscripcionPutDTO {
    private Long clienteId;
    private Long claseId;
    private boolean asistio = false;
}