package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Inscripcion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCorreo(String correo);

    @Query("SELECT i FROM Inscripcion i JOIN FETCH i.clase WHERE i.cliente.id = :clienteId")
    List<Inscripcion> findInscripcionesByClienteIdWithClase(@Param("clienteId") Long clienteId);

    boolean existsByCorreo(@NotBlank @Email String correo);
}