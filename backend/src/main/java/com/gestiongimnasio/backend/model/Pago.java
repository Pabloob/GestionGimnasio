package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones;

    @NotNull(message = "El monto no puede estar vacío")
    @Positive(message = "El monto debe ser un valor positivo")
    @Column(nullable = false)
    private Double monto;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @NotNull(message = "El estado de pago no puede estar vacío")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    private String comentario;

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, CANCELADO
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (estadoPago == EstadoPago.COMPLETADO && fechaPago == null) {
            fechaPago = LocalDate.now();
        }
    }
}