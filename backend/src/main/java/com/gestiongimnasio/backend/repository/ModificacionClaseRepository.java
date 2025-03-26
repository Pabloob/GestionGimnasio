package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.ModificacionClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModificacionClaseRepository extends JpaRepository<ModificacionClase, Integer> {
    List<ModificacionClase> findByClaseId(Integer claseId);
    List<ModificacionClase> findByInstructorId(Long instructorId);
}