package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    List<Trabajador> findByTipoTrabajador(Trabajador.TipoTrabajador tipo);

    boolean existsByCorreo(String correo);

    Optional<Trabajador> findByCorreo(String correo);




    @Query("SELECT t FROM Trabajador t LEFT JOIN FETCH t.clases WHERE t.id = :id")
    Optional<Trabajador> findByIdWithClases(@Param("id") Long id);
}