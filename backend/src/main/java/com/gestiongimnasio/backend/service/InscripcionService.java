package com.gestiongimnasio.backend.service;

import java.util.List;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;

public interface InscripcionService {
    InscripcionGetDTO getById(Long id);


    List<InscripcionGetDTO> getByCliente(Long clienteId);

    List<InscripcionGetDTO> getByClase(Long claseId);

    List<InscripcionGetDTO> getAll();

    InscripcionGetDTO save(InscripcionPostDTO claseDTO);

    InscripcionGetDTO update(Long id, InscripcionPutDTO claseDTO);

    void delete(Long id);

    boolean existsInscripcion(Long clienteId, Long claseId);

    InscripcionGetDTO confirmarPago(Long inscripcionId, Long pagoId);

    InscripcionGetDTO confirmarAsistencia(Long inscripcionId);
}