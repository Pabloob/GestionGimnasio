package com.gestiongimnasio.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestiongimnasio.backend.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    boolean existsByCorreo(String correo);

    Optional<Usuario> findByCorreo(String correo);

}
