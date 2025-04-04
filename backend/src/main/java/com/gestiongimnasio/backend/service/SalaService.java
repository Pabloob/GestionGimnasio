package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClaseService {
    ClaseGetDTO getById(Long id);

    @Transactional(readOnly = true)
    List<ClaseGetDTO> getClasesByCliente(Long clienteId);

    List<ClaseGetDTO> getAll();

    ClaseGetDTO getByNombre(String nombreClase);


    ClaseGetDTO save(ClasePostDTO claseDTO);

    ClaseGetDTO update(Long id, ClasePutDTO claseDTO);

    void delete(Long id);

    void confirmarAsistencias(Long idClase);

    void deactivate(Long id);

    List<ClaseGetDTO> getClasesByInstructor(Long instructorId);

    long countInscritosByClaseId(Long claseId);
}