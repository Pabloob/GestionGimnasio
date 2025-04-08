package com.gymmanagement.backend.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymmanagement.backend.model.FitnessClass;
import com.gymmanagement.backend.model.Horario;
import com.gymmanagement.backend.model.Sala;
import com.gestiongimnasio.backend.model.Trabajador;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ClaseHorarioRepository extends JpaRepository<Horario, Long> {


    List<Horario> findByClaseId(Long claseId);

    void deleteByClaseId(Long claseId);

    boolean existsByInstructorId(Long id);

    List<Horario> findByInstructorIdAndDiaSemana(Long trabajadorId, DayOfWeek dia);

    boolean existsBySalaId(Long id);

    List<Horario> findBySalaId(Long salaId);

    List<Horario> findByInstructorId(Long instructorId);

    List<Horario> findByDiaSemana(DayOfWeek dia);

    boolean existsByDiaSemanaAndSalaIdAndHoraInicioLessThanAndHoraFinGreaterThanAndIdNot(DayOfWeek dia, Long salaId, LocalTime horaFin, LocalTime horaInicio, Long excludeId);

    boolean existsByDiaSemanaAndInstructorIdAndHoraInicioLessThanAndHoraFinGreaterThanAndIdNot(DayOfWeek dia, Long instructorId, LocalTime horaFin, LocalTime horaInicio, Long excludeId);

    boolean existsByClaseAndSalaAndInstructorAndDiaSemanaAndHoraInicioAndHoraFin(FitnessClass clase, Sala sala, Trabajador instructor, @NotNull DayOfWeek diaSemana, @NotNull LocalTime horaInicio, @NotNull LocalTime horaFin);
}