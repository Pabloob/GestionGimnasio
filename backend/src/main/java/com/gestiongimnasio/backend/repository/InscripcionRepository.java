package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByClienteId(Long clienteId);

    List<Inscripcion> findByClaseId(Long claseId);

    int countByClaseId(Long clase_id);


    @Query("SELECT i FROM Inscripcion i WHERE i.id IN :ids")
    List<Inscripcion> findAllById(@Param("ids") List<Long> inscripcionesIds);

    boolean existsByClienteIdAndClaseId(Long clienteId, Long claseId);

}