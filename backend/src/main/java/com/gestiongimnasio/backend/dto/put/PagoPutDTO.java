package com.gestiongimnasio.backend.dto.put;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PagoPutDTO {
    private Long clienteId;
    @Positive
    private Double monto;
    private boolean pagado = false;
}