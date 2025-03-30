package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.model.ModificacionClase;
import com.gestiongimnasio.backend.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModificacionClaseRepository extends JpaRepository<ModificacionClase, Long> {



    Optional<ModificacionClase> findByClaseId(Long claseId);

    Optional<ModificacionClase> findByInstructorId(Long instructorId);

    List<ModificacionClase> findByInstructor(Trabajador instructor);

    long countByClaseId(Long claseId);
}