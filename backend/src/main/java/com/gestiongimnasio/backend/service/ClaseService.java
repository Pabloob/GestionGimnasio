package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;

import java.util.List;

public interface ClaseService {
    List<ClaseGetDTO> findAllClases();
    List<ClaseGetDTO> findClasesActivas();
    ClaseGetDTO findClaseById(Long id) ;
    ClaseGetDTO saveClase(ClasePostDTO clasePostDTO);
    ClaseGetDTO updateClase(Long id, ClasePutDTO clasePutDTO);
    void toggleClaseStatus(Long id)  ;
    boolean tieneCuposDisponibles(Long claseId)  ;
    int cuposDisponibles(Long claseId)  ;

    void deleteClase(Long id);
}