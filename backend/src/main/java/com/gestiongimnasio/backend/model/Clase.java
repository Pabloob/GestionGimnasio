package com.gestiongimnasio.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String nombre;

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
    @CollectionTable(name = "clase_dias", joinColumns = @JoinColumn(name = "id_clase"))
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<Dia> dias = new HashSet<>();

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Inscripcion> inscripciones = new HashSet<>();

    private boolean activa = true;

    public enum Dia {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    @PrePersist
    @PreUpdate
    private void validar() {
        if (horaFin.isBefore(horaInicio)) {
            throw new IllegalStateException("La hora de fin debe ser posterior a la hora de inicio");
        }
        if (dias.isEmpty()) {
            throw new IllegalStateException("Debe haber al menos un día seleccionado");
        }
    }
}