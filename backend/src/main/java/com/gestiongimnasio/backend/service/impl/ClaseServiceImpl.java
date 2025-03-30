package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.ClaseService;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.utils.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final ClaseRepository claseRepository;
    private final ClienteService clienteService;
    private final TrabajadorRepository trabajadorRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public ClaseGetDTO getById(Long id) {
        Clase clase = claseRepository.findWithInscripcionesById(id)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + id));
        return toResponseDTO(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseGetDTO> getClasesByInstructor(Long instructorId) {
        return claseRepository.findByInstructorIdAndActivaTrue(instructorId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseGetDTO> getAll() {
        return claseRepository.findAllWithRelations().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseGetDTO getByNombre(String nombreClase) {
        Clase clase = claseRepository.findByNombre(nombreClase)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con nombre: " + nombreClase));
        return toResponseDTO(clase);
    }

    @Override
    @Transactional()
    public ClaseGetDTO save(ClasePostDTO claseDTO) {
        if (claseDTO == null) {
            throw new IllegalArgumentException("Datos de la clase no pueden ser nulos");
        }

        Trabajador instructor = trabajadorRepository.findById(claseDTO.getInstructorId())
                .orElseThrow(() -> new BusinessException("Instructor no encontrado con ID: " + claseDTO.getInstructorId()));

        if (isInstructorAvailable(instructor, claseDTO.getHoraInicio(), claseDTO.getHoraFin(), claseDTO.getDias())) {
            throw new BusinessException("El instructor no está disponible en ese horario");
        }

        if (!isSalaDisponible(claseDTO.getSala(), claseDTO.getHoraInicio(), claseDTO.getHoraFin(), claseDTO.getDias())) {
            throw new BusinessException("La sala no está disponible en ese horario");
        }

        Clase clase = modelMapper.map(claseDTO, Clase.class);
        clase.setInstructor(instructor);
        clase.setActiva(true);

        Clase savedClase = claseRepository.save(clase);
        return toResponseDTO(savedClase);
    }

    @Override
    @Transactional()
    public ClaseGetDTO update(Long id, ClasePutDTO claseDTO) {
        if (!id.equals(claseDTO.getId())) {
            throw new IllegalArgumentException("ID de clase no coincide");
        }

        Clase claseExistente = claseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + id));

        if (claseDTO.getInstructorId() != null &&
                !claseExistente.getInstructor().getId().equals(claseDTO.getInstructorId())) {
            Trabajador nuevoInstructor = trabajadorRepository.findById(claseDTO.getInstructorId())
                    .orElseThrow(() -> new BusinessException("Instructor no encontrado con ID: " + claseDTO.getInstructorId()));

            LocalTime horaInicio = claseDTO.getHoraInicio() != null ? claseDTO.getHoraInicio() : claseExistente.getHoraInicio();
            LocalTime horaFin = claseDTO.getHoraFin() != null ? claseDTO.getHoraFin() : claseExistente.getHoraFin();
            Set<Clase.Dia> dias = claseDTO.getDias() != null ? claseDTO.getDias() : claseExistente.getDias();

            if (isInstructorAvailable(nuevoInstructor, horaInicio, horaFin, dias)) {
                throw new BusinessException("El instructor no está disponible en ese horario");
            }
            claseExistente.setInstructor(nuevoInstructor);
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(claseDTO, claseExistente);

        Clase updatedClase = claseRepository.save(claseExistente);
        return toResponseDTO(updatedClase);
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + id));
        claseRepository.delete(clase);
    }

    @Override
    @Transactional()
    public void confirmarAsistencias(Long idClase) {
        Clase clase = claseRepository.findById(idClase)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + idClase));

        clase.getInscripciones().stream()
                .filter(inscripcion -> !inscripcion.isAsistio())
                .forEach(inscripcion ->
                        clienteService.incrementarInasistencias(inscripcion.getCliente().getId())
                );
    }

    @Override
    @Transactional()
    public void deactivate(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + id));
        clase.setActiva(false);
        claseRepository.save(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public long countInscritosByClaseId(Long claseId) {
        return inscripcionRepository.countByClaseId(claseId);
    }

    private boolean isInstructorAvailable(Trabajador instructor, LocalTime horaInicio,
                                          LocalTime horaFin, Set<Clase.Dia> dias) {
        List<Clase> clasesExistentes = claseRepository.findByInstructorAndActivaTrue(instructor);
        return clasesExistentes.stream().anyMatch(claseExistente ->
                tieneSuperposicionHoraria(claseExistente, horaInicio, horaFin, dias)
        );
    }

    private boolean tieneSuperposicionHoraria(Clase claseExistente, LocalTime nuevaHoraInicio,
                                              LocalTime nuevaHoraFin, Set<Clase.Dia> nuevosDias) {
        return !Collections.disjoint(claseExistente.getDias(), nuevosDias) &&
                !(nuevaHoraFin.isBefore(claseExistente.getHoraInicio()) ||
                        nuevaHoraInicio.isAfter(claseExistente.getHoraFin()));
    }

    private boolean isSalaDisponible(String sala, LocalTime horaInicio, LocalTime horaFin, Set<Clase.Dia> dias) {
        List<Clase> clasesEnSala = claseRepository.findBySalaAndActivaTrue(sala);
        return clasesEnSala.stream().anyMatch(claseExistente ->
                tieneSuperposicionHoraria(claseExistente, horaInicio, horaFin, dias)
        );
    }

    private ClaseGetDTO toResponseDTO(Clase clase) {
        ClaseGetDTO dto = modelMapper.map(clase, ClaseGetDTO.class);

        if (clase.getInstructor() != null) {
            dto.setInstructorId(clase.getInstructor().getId());
            dto.setInstructorNombre(clase.getInstructor().getNombre());
        }

        dto.setTotalInscritos(inscripcionRepository.countByClaseId(clase.getId()));

        return dto;
    }
}