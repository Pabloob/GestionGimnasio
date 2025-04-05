package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.model.Trabajador;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @EntityGraph(attributePaths = {"inscripciones", "instructor"})
    @Query("SELECT c FROM Clase c")
    List<Clase> findAllWithRelations();

    @EntityGraph(attributePaths = {"inscripciones"})
    Optional<Clase> findWithInscripcionesById(Long id);

    List<Clase> findByActivaTrue();
}