package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El precio no puede estar vacío")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "La hora de inicio no puede estar vacía")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin no puede estar vacía")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", nullable = false)
    private Trabajador instructor;

    @NotNull(message = "La capacidad máxima no puede estar vacía")
    @Positive(message = "La capacidad máxima debe ser un valor positivo")
    @Column(name = "capacidad_max", nullable = false)
    private Integer capacidadMaxima;

    @NotBlank(message = "La sala no puede estar vacía")
    @Size(max = 100, message = "La sala no puede exceder los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String sala;

    @ElementCollection
    @CollectionTable(name = "clase_dias", joinColumns = @JoinColumn(name = "id_clase"))
    @Column(name = "dia", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Los días no pueden estar vacíos")
    private Set<Dia> dias;

    @Column(nullable = false)
    private boolean activa = true;

    public enum Dia {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
        if (dias == null || dias.isEmpty()) {
            throw new IllegalStateException("Debe haber al menos un día seleccionado");
        }
    }
}