package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCorreo(String correo);

    Optional<Cliente> findByCorreo(String correo);

    Optional<Cliente> getClienteByCorreoAndContrasena(String email, String password);

    Optional<Cliente> getClienteByCorreo(String correo);
}