package com.gestiongimnasio.backend.service;

import java.util.List;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;

public interface ClaseService {
    ClaseGetDTO getById(Long id);

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