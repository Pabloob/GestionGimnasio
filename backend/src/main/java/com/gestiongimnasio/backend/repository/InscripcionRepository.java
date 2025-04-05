package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByClienteId(Long clienteId);

    List<Inscripcion> findByClaseId(Long claseId);

    boolean existsByClienteIdAndClaseId(Long id, Long id1);
}