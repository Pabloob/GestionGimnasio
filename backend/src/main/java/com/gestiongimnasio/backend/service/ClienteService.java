package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;


import java.util.List;

public interface ClienteService {
    ClienteGetDTO getById(Long id);

    List<ClienteGetDTO> getAll();

    List<ClaseGetDTO> getClasesInscritas(Long clienteId);


    ClienteGetDTO save(ClientePostDTO registroDTO);

    ClienteGetDTO update(Long id, ClientePutDTO updateDTO);


    ClienteGetDTO incrementarInasistencias(Long id);

}