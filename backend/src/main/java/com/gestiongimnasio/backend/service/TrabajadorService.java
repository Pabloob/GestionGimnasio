package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.TrabajadorDTO;
import com.gestiongimnasio.backend.model.Trabajador;

import java.util.List;

public interface TrabajadorService {
    TrabajadorDTO saveTrabajador(TrabajadorDTO trabajadorDTO);
    List<TrabajadorDTO> getAllTrabajadores();
    TrabajadorDTO getTrabajadorById(Long id);
    TrabajadorDTO updateTrabajador(Long id, TrabajadorDTO trabajadorDTO);
    void deleteTrabajador(Long id);
    List<TrabajadorDTO> findByTipo(Trabajador.TipoTrabajador tipo);

    boolean existsByCorreo(String correo);

    Long authenticateCliente(String correo,String contrasena);
}