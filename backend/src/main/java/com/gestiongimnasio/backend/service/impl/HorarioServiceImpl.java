package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseHorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.ClaseHorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.ClaseHorarioPutDTO;
import com.gestiongimnasio.backend.mappers.ClaseHorarioMapper;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.ClaseHorarioService;
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
public class ClaseHorarioServiceImpl implements ClaseHorarioService {

    private final ClaseHorarioRepository claseHorarioRepository;
    private final ClaseRepository claseRepository;
    private final SalaRepository salaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ClaseHorarioMapper claseHorarioMapper;

    @Override
    public List<ClaseHorarioGetDTO> findAll() {
        return claseHorarioRepository.findAll()
                .stream()
                .map(claseHorarioMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaseHorarioGetDTO findById(Long id) {
        ClaseHorario claseHorario = claseHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de clase no encontrado con ID: " + id));
        return claseHorarioMapper.toGetDto(claseHorario);
    }

    @Override
    public ClaseHorarioGetDTO create(ClaseHorarioPostDTO dto) {
        // Validar entidades relacionadas
        Clase clase = validateEntity((BaseRepository<Clase, Long>) claseRepository, dto.getClase(), "Clase");
        Sala sala = validateEntity((BaseRepository<Sala, Long>) salaRepository, dto.getSala(), "Sala");
        Trabajador instructor = validateEntity((BaseRepository<Trabajador, Long>) trabajadorRepository, dto.getInstructor(), "Instructor");

        // Validar horario
        validateSchedule(dto.getHoraInicio(), dto.getHoraFin());

        // Validar disponibilidad de sala e instructor
        validateAvailability(dto.getDiaSemana(), dto.getHoraInicio(), dto.getHoraFin(),
                dto.getSala(), dto.getInstructor(), null);

        ClaseHorario claseHorario = claseHorarioMapper.toEntity(dto);
        setRelations(claseHorario, clase, sala, instructor);

        clase.addHorario(claseHorario);
        sala.addHorario(claseHorario);
        instructor.addHorario(claseHorario);

        ClaseHorario savedClaseHorario = claseHorarioRepository.save(claseHorario);
        return claseHorarioMapper.toGetDto(savedClaseHorario);
    }

    @Override
    public ClaseHorarioGetDTO update(Long id, ClaseHorarioPutDTO dto) {
        ClaseHorario claseHorario = claseHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de clase no encontrado con ID: " + id));

        // Obtener entidades relacionadas si se están actualizando
        Clase clase = dto.getClase() != null ?
                validateEntity((BaseRepository<Clase, Long>) claseRepository, dto.getClase(), "Clase") :
                claseHorario.getClase();

        Sala sala = dto.getSala() != null ?
                validateEntity((BaseRepository<Sala, Long>) salaRepository, dto.getSala(), "Sala") :
                claseHorario.getSala();

        Trabajador instructor = dto.getInstructor() != null ?
                validateEntity((BaseRepository<Trabajador, Long>) trabajadorRepository, dto.getInstructor(), "Instructor") :
                claseHorario.getInstructor();

        // Validar horario si se está actualizando
        LocalTime horaInicio = dto.getHoraInicio() != null ? dto.getHoraInicio() : claseHorario.getHoraInicio();
        LocalTime horaFin = dto.getHoraFin() != null ? dto.getHoraFin() : claseHorario.getHoraFin();
        validateSchedule(horaInicio, horaFin);

        // Validar disponibilidad
        DayOfWeek dia = dto.getDiaSemana() != null ? dto.getDiaSemana() : claseHorario.getDiaSemana();
        Long salaId = dto.getSala() != null ? dto.getSala() : claseHorario.getSala().getId();
        Long instructorId = dto.getInstructor() != null ? dto.getInstructor() : claseHorario.getInstructor().getId();

        validateAvailability(dia, horaInicio, horaFin, salaId, instructorId, id);

        // Actualizar entidad
        clase.addHorario(claseHorario);
        sala.addHorario(claseHorario);
        instructor.addHorario(claseHorario);

        claseHorarioMapper.updateFromDto(dto, claseHorario);
        setRelations(claseHorario, clase, sala, instructor);

        ClaseHorario updatedClaseHorario = claseHorarioRepository.save(claseHorario);
        return claseHorarioMapper.toGetDto(updatedClaseHorario);
    }

    @Override
    public void delete(Long id) {
        if (!claseHorarioRepository.existsById(id)) {
            throw new RuntimeException("Horario de clase no encontrado con ID: " + id);
        }
        claseHorarioRepository.deleteById(id);
    }

    @Override
    public List<ClaseHorarioGetDTO> findByClase(Long claseId) {
        return claseHorarioRepository.findByClaseId(claseId)
                .stream()
                .map(claseHorarioMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaseHorarioGetDTO> findBySala(Long salaId) {
        return claseHorarioRepository.findBySalaId(salaId)
                .stream()
                .map(claseHorarioMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaseHorarioGetDTO> findByInstructor(Long instructorId) {
        return claseHorarioRepository.findByInstructorId(instructorId)
                .stream()
                .map(claseHorarioMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaseHorarioGetDTO> findByDia(DayOfWeek dia) {
        return claseHorarioRepository.findByDiaSemana(dia)
                .stream()
                .map(claseHorarioMapper::toGetDto)
                .collect(Collectors.toList());
    }

    private <T> T validateEntity(BaseRepository<T, Long> repository, Long entityId, String entityName) {
        return repository.findById(entityId)
                .orElseThrow(() -> new RuntimeException(entityName + " no encontrada con ID: " + entityId));
    }

    private void setRelations(ClaseHorario claseHorario, Clase clase, Sala sala, Trabajador instructor) {
        claseHorario.setClase(clase);
        claseHorario.setSala(sala);
        claseHorario.setInstructor(instructor);
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