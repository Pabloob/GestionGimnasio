package com.gestiongimnasio.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("inscripciones")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("inscripciones")
    private Clase clase;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pago pago;

    @NotNull
    @Column(updatable = false)
    private LocalDate fechaRegistro;

    private boolean asistio = false;

    @NotNull
    private Boolean estadoPago = false;

    @PrePersist
    private void setFechaRegistro() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }
}