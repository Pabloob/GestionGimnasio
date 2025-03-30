package com.gestiongimnasio.backend.dto.get;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InscripcionGetDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private Long claseId;
    private String claseNombre;
    private Long pagoId;
    private LocalDate fechaRegistro;
    private Boolean estadoPago;
}