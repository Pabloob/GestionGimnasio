package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;

import java.util.List;

public interface InscripcionService {
    List<InscripcionGetDTO> findAllInscripciones();
    InscripcionGetDTO findInscripcionById(Long id)  ;
    InscripcionGetDTO saveInscripcion(InscripcionPostDTO inscripcionPostDTO);
    InscripcionGetDTO updateInscripcion(Long id, InscripcionPutDTO inscripcionPutDTO)  ;
    void deleteInscripcion(Long id)  ;
    List<InscripcionGetDTO> findByCliente(Long clienteId);
    List<InscripcionGetDTO> findByClase(Long claseId);
    void registrarAsistencia(Long inscripcionId)  ;
}