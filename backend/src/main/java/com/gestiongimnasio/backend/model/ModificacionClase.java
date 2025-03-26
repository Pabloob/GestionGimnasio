package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "modificaion_clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificacionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false)
    private Clase clase;

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
    @CollectionTable(name = "modificacion_clase_dias", joinColumns = @JoinColumn(name = "id_modificacion"))
    @Column(name = "dia", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Los días no pueden estar vacíos")
    private Set<Clase.Dia> dias;

    @NotNull(message = "La fecha de modificacion no puede estar vacía")
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDate fechaModificacion;

    @Size(max = 100, message = "El comentario no puede exceder los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String comentario;
}

