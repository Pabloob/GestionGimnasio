package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.ModificacionClaseDTO;
import com.gestiongimnasio.backend.model.ModificacionClase;

import java.util.List;

public interface ModificacionClaseService {
    ModificacionClaseDTO saveModificacion(ModificacionClaseDTO modificacionClaseDTO);
    List<ModificacionClaseDTO> getAllModificaciones();
    ModificacionClaseDTO getModificacionById(Integer id);
    List<ModificacionClaseDTO> getModificacionesByClase(Integer claseId);
    List<ModificacionClaseDTO> getModificacionesByInstructor(Long instructorId);
}