package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.HorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.HorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.HorarioPutDTO;

import java.time.DayOfWeek;
import java.util.List;

public interface HorarioService {
    List<HorarioGetDTO> findAll();
    HorarioGetDTO findById(Long id);
    HorarioGetDTO create(HorarioPostDTO horarioPostDTO);
    HorarioGetDTO update(Long id, HorarioPutDTO horarioPutDTO);
    void delete(Long id)  ;
    List<HorarioGetDTO> findByClase(Long claseId);
    List<HorarioGetDTO> findBySala(Long salaId);
    List<HorarioGetDTO> findByInstructor(Long instructorId);
    List<HorarioGetDTO> findByDia(DayOfWeek dia);
}