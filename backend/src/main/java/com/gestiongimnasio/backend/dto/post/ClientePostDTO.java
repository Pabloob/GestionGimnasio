package com.gestiongimnasio.backend.dto.post;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientePostDTO {

    @NotNull
    UsuarioPostDTO usuarioPostDTO;

}