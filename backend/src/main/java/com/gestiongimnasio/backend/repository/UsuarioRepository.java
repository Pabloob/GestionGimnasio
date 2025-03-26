package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.OptionalInt;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> getUsuarioByCorreo(String correo);


}
