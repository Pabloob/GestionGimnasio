package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @NotNull @Positive
    private Double monto;

    private LocalDate fechaPago;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @Size(max = 500)
    private String comentario;

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, CANCELADO
    }

    @PrePersist
    @PreUpdate
    private void validar() {
        if (estadoPago == EstadoPago.COMPLETADO && fechaPago == null) {
            fechaPago = LocalDate.now();
        }
    }
}