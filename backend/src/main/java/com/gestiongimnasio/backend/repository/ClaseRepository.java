package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Integer> {
    List<Clase> findByInstructorId(Long instructorId);
    List<Clase> findByNombreContainingIgnoreCase(String nombre);
}