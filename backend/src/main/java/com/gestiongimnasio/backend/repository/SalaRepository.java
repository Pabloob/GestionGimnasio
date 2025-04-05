package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {


    boolean existsByNombre(String nombre);
}