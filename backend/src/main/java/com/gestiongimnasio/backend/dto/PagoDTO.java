package com.gestiongimnasio.backend.dto;

import com.gestiongimnasio.backend.model.Pago.EstadoPago;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    @NotNull(message = "El cliente no puede estar vacío")
    private Long idCliente;

    @NotNull(message = "El monto no puede estar vacío")
    @Positive(message = "El monto debe ser un valor positivo")
    private Double monto;

    private LocalDate fechaPago;

    @NotNull(message = "El estado de pago no puede estar vacío")
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    private String comentario;
}