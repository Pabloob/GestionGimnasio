package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface TrabajadorService {
    List<TrabajadorGetDTO> findAllTrabajadores();
    TrabajadorGetDTO findTrabajadorById(Long id);
    TrabajadorGetDTO saveTrabajador(TrabajadorPostDTO trabajadorPostDTO);
    TrabajadorGetDTO updateTrabajador(Long id, TrabajadorPutDTO trabajadorPutDTO);
    void deleteTrabajador(Long id);
    List<TrabajadorGetDTO> findByTipo(String tipoTrabajador);
    boolean estaDisponible(Long trabajadorId, DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin);
}