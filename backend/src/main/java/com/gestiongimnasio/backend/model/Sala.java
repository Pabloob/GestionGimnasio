package com.gestiongimnasio.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotNull
    private LocalTime horaApertura;

    @NotNull
    private LocalTime horaCierre;

    @ElementCollection
    @CollectionTable(name = "sala_dias_disponibles", joinColumns = @JoinColumn(name = "sala_id"))
    @Column(name = "dia_semana")
    private Set<Integer> diasDisponibles = new HashSet<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();

    public boolean estaDisponible(Clase.Dia dia, LocalTime horaInicio, LocalTime horaFin) {
        return diasDisponibles.contains(dia.getValue()) &&
                !horaInicio.isBefore(horaApertura) &&
                !horaFin.isAfter(horaCierre) &&
                reservas.stream().noneMatch(r -> r.enConflictoCon(dia.getValue(), horaInicio, horaFin));
    }
}