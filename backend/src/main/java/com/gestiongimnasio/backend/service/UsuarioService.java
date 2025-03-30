package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.UsuarioLoginDTO;
import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;
import com.gestiongimnasio.backend.dto.post.UsuarioPostDTO;
import com.gestiongimnasio.backend.dto.put.UsuarioPutDTO;

import java.util.List;

public interface UsuarioService {
    Object authenticate(UsuarioLoginDTO loginDTO);
    UsuarioGetDTO getById(Long id);
    UsuarioGetDTO getByCorreo(String correo);
    List<UsuarioGetDTO> getAll();
    UsuarioGetDTO save(UsuarioPostDTO registroDTO);
    UsuarioGetDTO update(Long id, UsuarioPutDTO updateDTO);
    boolean existsByCorreo(String correo);

    void deactivate(Long id);
}
