package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.model.Trabajador;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    Optional<Clase> findByNombre(String nombre);


    List<Clase> findByInstructorIdAndActivaTrue(Long instructorId);

    List<Clase> findByInstructorAndActivaTrue(Trabajador instructor);


    List<Clase> findByInstructor(Trabajador instructor);

    List<Clase> findBySalaAndActivaTrue(String sala);

    @EntityGraph(attributePaths = {"inscripciones", "instructor"})
    @Query("SELECT c FROM Clase c")
    List<Clase> findAllWithRelations();

    @EntityGraph(attributePaths = {"inscripciones"})
    Optional<Clase> findWithInscripcionesById(Long id);
}