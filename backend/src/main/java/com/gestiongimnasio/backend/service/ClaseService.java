package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.ClaseDTO;
import com.gestiongimnasio.backend.model.Clase;

import java.util.List;

public interface ClaseService {
    ClaseDTO saveClase(ClaseDTO claseDTO);
    List<ClaseDTO> getAllClases();
    ClaseDTO getClaseById(Integer id);
    ClaseDTO updateClase(Integer id, ClaseDTO claseDTO);
    void deleteClase(Integer id);
    List<ClaseDTO> getClasesByInstructor(Long instructorId);
    List<ClaseDTO> searchByNombre(String nombre);
}