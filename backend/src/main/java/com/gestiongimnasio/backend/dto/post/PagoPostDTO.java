package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class PagoPostDTO {
    @NotNull
    private Long clienteId;

    @NotNull @Positive
    private Double monto;

    private List<Integer> inscripcionesIds;

    @Size(max = 500)
    private String comentario;
}