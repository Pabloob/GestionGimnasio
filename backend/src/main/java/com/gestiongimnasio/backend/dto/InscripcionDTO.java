package com.gestiongimnasio.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {

    @NotNull(message = "El cliente no puede estar vacío")
    private Long idCliente;

    @NotNull(message = "La clase no puede estar vacía")
    private Integer idClase;

    private Integer idPago;

    @NotNull(message = "La fecha de registro no puede estar vacía")
    private LocalDate fechaRegistro;

    @NotNull(message = "El estado de pago no puede estar vacío")
    private Boolean estadoPago = false;
}