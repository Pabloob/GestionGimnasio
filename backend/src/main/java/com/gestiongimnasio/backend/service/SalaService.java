package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.SalaGetDTO;
import com.gestiongimnasio.backend.dto.post.SalaPostDTO;
import com.gestiongimnasio.backend.dto.put.SalaPutDTO;

import java.util.List;

public interface SalaService {
    List<SalaGetDTO> findAllSalas();

    SalaGetDTO findSalaById(Long id);

    SalaGetDTO saveSala(SalaPostDTO salaPostDTO);

    SalaGetDTO updateSala(Long id, SalaPutDTO salaPutDTO);

    void deleteSala(Long id);

}