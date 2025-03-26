package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.InscripcionDTO;
import com.gestiongimnasio.backend.model.Inscripcion;

import java.util.List;

public interface InscripcionService {
    InscripcionDTO saveInscripcion(InscripcionDTO inscripcionDTO);
    List<InscripcionDTO> getAllInscripciones();
    InscripcionDTO getInscripcionById(Integer id);
    InscripcionDTO updateInscripcion(Integer id, InscripcionDTO inscripcionDTO);
    void deleteInscripcion(Integer id);
    List<InscripcionDTO> getInscripcionesByCliente(Long clienteId);
    List<InscripcionDTO> getInscripcionesByClase(Integer claseId);
    boolean existsInscripcion(Long clienteId, Integer claseId);
}