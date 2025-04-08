package com.gymmanagement.backend.dto.get;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PagoGetDTO {
    private Long id;
    private ClienteGetDTO cliente;
    private Double monto;
    private LocalDate fechaPago;
    private boolean pagado;
}