package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class PagoPostDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    @Positive
    private Double monto;

    private boolean pagado = false;
}