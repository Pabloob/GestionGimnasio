package com.gestiongimnasio.backend.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClienteDTO extends UsuarioDTO {

    @PositiveOrZero(message = "Las inasistencias no pueden ser negativas")
    private int inasistencias = 0;

}