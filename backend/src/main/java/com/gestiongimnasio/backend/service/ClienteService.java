package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteGetDTO> findAllClientes();
    ClienteGetDTO findClienteById(Long id);
    ClienteGetDTO saveCliente(ClientePostDTO clientePostDTO);
    ClienteGetDTO updateCliente(Long id, ClientePutDTO clientePutDTO);
    void deleteCliente(Long id);
}