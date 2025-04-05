package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.UsuarioLoginDTO;
import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;

import java.util.List;

public interface UsuarioService {
    Object authenticate(UsuarioLoginDTO loginDTO);
    UsuarioGetDTO findById(Long id);
    UsuarioGetDTO getByCorreo(String correo);
    List<UsuarioGetDTO> getAll();
    boolean existsByCorreo(String correo);

    void toggleUsuarioStatus(Long id);
}
