package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "modificaciones_clase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificacionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Clase clase;

    @NotNull @Positive
    private Double precio;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Trabajador instructor;

    @NotNull @Positive
    private Integer capacidadMaxima;

    @NotBlank @Size(max = 100)
    private String sala;

    @ElementCollection
    @CollectionTable(name = "modificacion_clase_dias", joinColumns = @JoinColumn(name = "id_modificacion"))
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<Clase.Dia> dias = new HashSet<>();

    @NotNull
    private LocalDate fechaModificacion = LocalDate.now();

    @Size(max = 100)
    private String comentario;

    @ManyToOne
    private Trabajador modificadoPor;
}