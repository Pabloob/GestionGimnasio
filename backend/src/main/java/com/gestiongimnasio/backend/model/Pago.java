package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cliente"})
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull
    @Positive
    private Double monto;

    @NotNull
    private LocalDate fechaPago;

    private boolean pagado = false;


    @PrePersist
    private void initFechaPago() {
        if (fechaPago == null) {
            fechaPago = LocalDate.now();
        }
    }
}