package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.ClienteDTO;


import java.util.List;

public interface ClienteService {
    ClienteDTO saveCliente(ClienteDTO clienteDTO);
    List<ClienteDTO> getAllClientes();
    ClienteDTO getClienteById(Long id);
    ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO);
    void deleteCliente(Long id);
    ClienteDTO incrementarInasistencias(Long id);

    boolean existsByCorreo(String correo);

    Long authenticateCliente(String correo, String contrasena);

    ClienteDTO obtenerClientePorCorreo(String correo);
}