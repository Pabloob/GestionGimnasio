package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InscripcionPostDTO {
    @NotNull
    private Long clienteId;

    @NotNull
    private Integer claseId;

    private Integer pagoId;
}