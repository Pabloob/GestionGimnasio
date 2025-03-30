package com.gestiongimnasio.backend.service;

import java.util.List;

import com.gestiongimnasio.backend.dto.get.ModificacionClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ModificacionClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ModificacionClasePutDTO;

public interface ModificacionClaseService {
    ModificacionClaseGetDTO getById(Long id);
    ModificacionClaseGetDTO getByClase(Long id);
    ModificacionClaseGetDTO getByInstructor(Long id);

    List<ModificacionClaseGetDTO> getAll();


    ModificacionClaseGetDTO save(ModificacionClasePostDTO modificacionClaseDTO);

    ModificacionClaseGetDTO update(Long id, ModificacionClasePutDTO modificacionClaseDTO);

    long countInscritosByClaseId(Long claseId);

    void delete(Long id);
}