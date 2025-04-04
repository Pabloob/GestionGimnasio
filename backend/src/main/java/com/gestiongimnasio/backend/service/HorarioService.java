package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.ClaseHorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.ClaseHorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.ClaseHorarioPutDTO;

import java.time.DayOfWeek;
import java.util.List;

public interface ClaseHorarioService {
    List<ClaseHorarioGetDTO> findAll();
    ClaseHorarioGetDTO findById(Long id);
    ClaseHorarioGetDTO create(ClaseHorarioPostDTO claseHorarioPostDTO);
    ClaseHorarioGetDTO update(Long id, ClaseHorarioPutDTO claseHorarioPutDTO);
    void delete(Long id)  ;
    List<ClaseHorarioGetDTO> findByClase(Long claseId);
    List<ClaseHorarioGetDTO> findBySala(Long salaId);
    List<ClaseHorarioGetDTO> findByInstructor(Long instructorId);
    List<ClaseHorarioGetDTO> findByDia(DayOfWeek dia);
}