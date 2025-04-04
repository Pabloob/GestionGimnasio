package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.ClaseHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ClaseHorario, Long> {


    List<ClaseHorario> findByClaseId(Long claseId);

    void deleteByClaseId(Long claseId);

    boolean existsByInstructorId(Long id);

    List<ClaseHorario> findByInstructorIdAndDiaSemana(Long trabajadorId, DayOfWeek dia);

    boolean existsBySalaId(Long id);

    List<ClaseHorario> findBySalaId(Long salaId);

    List<ClaseHorario> findByInstructorId(Long instructorId);

    List<ClaseHorario> findByDiaSemana(DayOfWeek dia);
}