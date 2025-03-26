package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Usuario {

    @PositiveOrZero(message = "Las inasistencias no pueden ser negativas")
    @Column(nullable = false)
    private int inasistencias = 0;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (getTipoUsuario() != Usuario.TipoUsuario.CLIENTE) {
            throw new IllegalStateException("El tipo de usuario debe ser CLIENTE");
        }
    }
}
