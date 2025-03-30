package com.gestiongimnasio.backend.dto.put;

import com.gestiongimnasio.backend.model.Pago;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PagoPutDTO {
    @NotNull
    Long id;
    private Long clienteId;
    @Positive
    private Double monto;
    private LocalDate fechaPago;
    private Pago.EstadoPago estadoPago;
    @Size(max = 500)
    private String comentario;
    private List<Long> inscripcionesIds;
}