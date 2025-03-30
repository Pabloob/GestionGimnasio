package com.gestiongimnasio.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Usuario {

    @PositiveOrZero
    private int inasistencias = 0;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pago> pagos = new ArrayList<>();

    @Override
    protected void validacionesEspecificas() {
        if (getTipoUsuario() != TipoUsuario.CLIENTE) {
            throw new IllegalStateException("El tipo de usuario debe ser CLIENTE");
        }
    }
}