package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.HorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.HorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.HorarioPutDTO;
import com.gestiongimnasio.backend.mappers.HorarioMapper;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final ClaseHorarioRepository claseHorarioRepository;
    private final ClaseRepository claseRepository;
    private final SalaRepository salaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final HorarioMapper horarioMapper;

    @Override
    public List<HorarioGetDTO> findAll() {
        return claseHorarioRepository.findAll()
                .stream()
                .map(horarioMapper::toHorarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioGetDTO findById(Long id) {
        Horario horario = claseHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de clase no encontrado con ID: " + id));
        return horarioMapper.toHorarioGetDTO(horario);
    }

    @Override
    public HorarioGetDTO create(HorarioPostDTO dto) {

        // Validar entidades relacionadas
        Clase clase = claseRepository.findById(dto.getClaseId()).orElseThrow();
        Sala sala = salaRepository.findById(dto.getSalaId()).orElseThrow();
        Trabajador instructor = trabajadorRepository.findById(dto.getInstructorId()).orElseThrow();

        // Validar si el horario ya existe
        boolean exists = claseHorarioRepository.existsByClaseAndSalaAndInstructorAndDiaSemanaAndHoraInicioAndHoraFin(
                clase, sala, instructor, dto.getDiaSemana(), dto.getHoraInicio(), dto.getHoraFin()
        );

        if (exists) {
            throw new IllegalArgumentException("Ya existe un horario con la misma clase, sala, instructor, día y horario.");
        }

        // Validar horario
        validateSchedule(dto.getHoraInicio(), dto.getHoraFin());

        // Validar disponibilidad de sala e instructor
        validateAvailability(dto.getDiaSemana(), dto.getHoraInicio(), dto.getHoraFin(),
                dto.getSalaId(), dto.getInstructorId(), null);

        Horario horario = horarioMapper.fromHorarioPostDTO(dto);
        setRelations(horario, clase, sala, instructor);

        Horario savedHorario = claseHorarioRepository.save(horario);
        return horarioMapper.toHorarioGetDTO(savedHorario);
    }


    @Override
    public HorarioGetDTO update(Long id, HorarioPutDTO dto) {
        Horario horario = claseHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de clase no encontrado con ID: " + id));

        // Obtener entidades relacionadas si se están actualizando
        Clase clase = dto.getClaseId() != null ?
                claseRepository.findById(dto.getClaseId()).orElseThrow() :
                horario.getClase();

        Sala sala = dto.getSalaId() != null ?
                salaRepository.findById(dto.getSalaId()).orElseThrow() :
                horario.getSala();

        Trabajador instructor = dto.getInstructorId() != null ?
                trabajadorRepository.findById(dto.getInstructorId()).orElseThrow() :
                horario.getInstructor();

        // Validar horario si se está actualizando
        LocalTime horaInicio = dto.getHoraInicio() != null ? dto.getHoraInicio() : horario.getHoraInicio();
        LocalTime horaFin = dto.getHoraFin() != null ? dto.getHoraFin() : horario.getHoraFin();
        validateSchedule(horaInicio, horaFin);

        // Validar disponibilidad
        DayOfWeek dia = dto.getDiaSemana() != null ? dto.getDiaSemana() : horario.getDiaSemana();
        Long salaId = dto.getSalaId() != null ? dto.getSalaId() : horario.getSala().getId();
        Long instructorId = dto.getInstructorId() != null ? dto.getInstructorId() : horario.getInstructor().getId();

        validateAvailability(dia, horaInicio, horaFin, salaId, instructorId, id);

        // Actualizar entidad
        horarioMapper.fromHorarioPutDTO(dto, horario);
        setRelations(horario, clase, sala, instructor);

        Horario updatedHorario = claseHorarioRepository.save(horario);
        return horarioMapper.toHorarioGetDTO(updatedHorario);
    }

    @Override
    public void delete(Long id) {
        if (!claseHorarioRepository.existsById(id)) {
            throw new RuntimeException("Horario de clase no encontrado con ID: " + id);
        }
        claseHorarioRepository.deleteById(id);
    }

    @Override
    public List<HorarioGetDTO> findByClase(Long claseId) {
        return claseHorarioRepository.findByClaseId(claseId)
                .stream()
                .map(horarioMapper::toHorarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HorarioGetDTO> findBySala(Long salaId) {
        return claseHorarioRepository.findBySalaId(salaId)
                .stream()
                .map(horarioMapper::toHorarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HorarioGetDTO> findByInstructor(Long instructorId) {
        return claseHorarioRepository.findByInstructorId(instructorId)
                .stream()
                .map(horarioMapper::toHorarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HorarioGetDTO> findByDia(DayOfWeek dia) {
        return claseHorarioRepository.findByDiaSemana(dia)
                .stream()
                .map(horarioMapper::toHorarioGetDTO)
                .collect(Collectors.toList());
    }


    private void setRelations(Horario horario, Clase clase, Sala sala, Trabajador instructor) {
        horario.setClase(clase);
        horario.setSala(sala);
        horario.setInstructor(instructor);
    }

    private void validateSchedule(LocalTime horaInicio, LocalTime horaFin) {
        if (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio)) {
            throw new RuntimeException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }

    private void validateAvailability(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin,
                                      Long salaId, Long instructorId, Long excludeId) {
        // Validar disponibilidad de sala
        boolean salaDisponible = claseHorarioRepository
                .existsByDiaSemanaAndSalaIdAndHoraInicioLessThanAndHoraFinGreaterThanAndIdNot(
                        dia, salaId, horaFin, horaInicio, excludeId);

        if (salaDisponible) {
            throw new RuntimeException("La sala ya está ocupada en ese horario");
        }

        // Validar disponibilidad de instructor
        boolean instructorDisponible = claseHorarioRepository
                .existsByDiaSemanaAndInstructorIdAndHoraInicioLessThanAndHoraFinGreaterThanAndIdNot(
                        dia, instructorId, horaFin, horaInicio, excludeId);

        if (instructorDisponible) {
            throw new RuntimeException("El instructor ya tiene una clase asignada en ese horario");
        }
    }
}