package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Usuario {

    @Override
    protected void validacionesEspecificas() {
        if (getTipoUsuario() != TipoUsuario.CLIENTE) {
            throw new IllegalStateException("El tipo de usuario debe ser CLIENTE");
        }
    }
}