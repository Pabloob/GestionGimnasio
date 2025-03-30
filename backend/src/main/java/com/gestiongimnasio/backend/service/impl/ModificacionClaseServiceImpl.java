package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ModificacionClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ModificacionClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ModificacionClasePutDTO;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.ModificacionClaseService;
import com.gestiongimnasio.backend.utils.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ModificacionClaseServiceImpl implements ModificacionClaseService {

    private final ModificacionClaseRepository modificacionClaseRepository;
    private final ClaseRepository claseRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public long countInscritosByClaseId(Long claseId) {
        return modificacionClaseRepository.countByClaseId(claseId);
    }

    @Override
    @Transactional(readOnly = true)
    public ModificacionClaseGetDTO getById(Long id) {
        ModificacionClase modificacion = modificacionClaseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Modificación de clase no encontrada con ID: " + id));
        return toResponseDTO(modificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public ModificacionClaseGetDTO getByClase(Long id) {
        ModificacionClase modificacion = modificacionClaseRepository.findByClaseId(id)
                .orElseThrow(() -> new RuntimeException("Modificación de clase no encontrada"));
        return toResponseDTO(modificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public ModificacionClaseGetDTO getByInstructor(Long id) {
        ModificacionClase modificacion = modificacionClaseRepository.findByInstructorId(id)
                .orElseThrow(() -> new RuntimeException("Modificación de clase no encontrada"));
        return toResponseDTO(modificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModificacionClaseGetDTO> getAll() {
        return modificacionClaseRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModificacionClaseGetDTO save(ModificacionClasePostDTO modificacionDTO) {
        validateModificacionPostDTO(modificacionDTO);

        Clase clase = claseRepository.findById(modificacionDTO.getClaseId())
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + modificacionDTO.getClaseId()));

        Trabajador instructor = trabajadorRepository.findById(modificacionDTO.getInstructorId())
                .orElseThrow(() -> new BusinessException("Instructor no encontrado con ID: " + modificacionDTO.getInstructorId()));

        Trabajador modificadoPor = trabajadorRepository.findById(modificacionDTO.getModificadoPorId())
                .orElseThrow(() -> new BusinessException("Trabajador no encontrado con ID: " + modificacionDTO.getModificadoPorId()));

        validateInstructorAvailability(instructor,
                modificacionDTO.getHoraInicio(),
                modificacionDTO.getHoraFin(),
                modificacionDTO.getDias());

        ModificacionClase modificacion = modelMapper.map(modificacionDTO, ModificacionClase.class);
        modificacion.setClase(clase);
        modificacion.setInstructor(instructor);
        modificacion.setModificadoPor(modificadoPor);
        modificacion.setFechaModificacion(LocalDate.now());

        return toResponseDTO(modificacionClaseRepository.save(modificacion));
    }

    @Override
    public ModificacionClaseGetDTO update(Long id, ModificacionClasePutDTO modificacionDTO) {
        if (!id.equals(modificacionDTO.getId())) {
            throw new IllegalArgumentException("ID de modificación no coincide");
        }

        ModificacionClase modificacion = modificacionClaseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Modificación no encontrada con ID: " + id));

        if (modificacionDTO.getInstructorId() != null) {
            Trabajador nuevoInstructor = trabajadorRepository.findById(modificacionDTO.getInstructorId())
                    .orElseThrow(() -> new BusinessException("Instructor no encontrado con ID: " + modificacionDTO.getInstructorId()));

            validateInstructorAvailability(nuevoInstructor,
                    modificacionDTO.getHoraInicio() != null ? modificacionDTO.getHoraInicio() : modificacion.getHoraInicio(),
                    modificacionDTO.getHoraFin() != null ? modificacionDTO.getHoraFin() : modificacion.getHoraFin(),
                    modificacionDTO.getDias() != null ? modificacionDTO.getDias() : modificacion.getDias());

            modificacion.setInstructor(nuevoInstructor);
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(modificacionDTO, modificacion);

        return toResponseDTO(modificacionClaseRepository.save(modificacion));
    }

    @Override
    public void delete(Long id) {
        ModificacionClase modificacion = modificacionClaseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Modificación no encontrada con ID: " + id));
        modificacionClaseRepository.delete(modificacion);
    }

    private void validateModificacionPostDTO(ModificacionClasePostDTO modificacionDTO) {
        if (modificacionDTO == null) {
            throw new IllegalArgumentException("Datos de modificación no pueden ser nulos");
        }
    }

    private void validateInstructorAvailability(Trabajador instructor, LocalTime horaInicio,
                                                LocalTime horaFin, Set<Clase.Dia> dias) {
        if (isInstructorAvailable(instructor, horaInicio, horaFin, dias)) {
            throw new BusinessException("El instructor no está disponible en ese horario");
        }
    }

    private boolean isInstructorAvailable(Trabajador instructor, LocalTime horaInicio,
                                          LocalTime horaFin, Set<Clase.Dia> dias) {
        // Verificar disponibilidad del instructor en clases regulares
        List<Clase> clasesInstructor = claseRepository.findByInstructor(instructor);
        boolean conflictoClases = clasesInstructor.stream().anyMatch(clase ->
                tieneSuperposicionHoraria(clase.getHoraInicio(), clase.getHoraFin(), clase.getDias(), horaInicio, horaFin, dias)
        );

        // Verificar disponibilidad en modificaciones de clase
        List<ModificacionClase> modificacionesInstructor = modificacionClaseRepository.findByInstructor(instructor);
        boolean conflictoModificaciones = modificacionesInstructor.stream().anyMatch(mod ->
                tieneSuperposicionHoraria(mod.getHoraInicio(), mod.getHoraFin(), mod.getDias(), horaInicio, horaFin, dias)
        );

        return conflictoClases || conflictoModificaciones;
    }

    private boolean tieneSuperposicionHoraria(LocalTime inicioExistente, LocalTime finExistente,
                                              Set<Clase.Dia> diasExistentes,
                                              LocalTime nuevaInicio, LocalTime nuevaFin,
                                              Set<Clase.Dia> nuevosDias) {
        boolean diasSuperpuestos = !Collections.disjoint(diasExistentes, nuevosDias);
        boolean horarioSuperpuesto = !(nuevaFin.isBefore(inicioExistente) || nuevaInicio.isAfter(finExistente));
        return diasSuperpuestos && horarioSuperpuesto;
    }

    private ModificacionClaseGetDTO toResponseDTO(ModificacionClase modificacion) {
        ModificacionClaseGetDTO dto = modelMapper.map(modificacion, ModificacionClaseGetDTO.class);

        if (modificacion.getClase() != null) {
            dto.setClaseId(modificacion.getClase().getId());
            dto.setClaseNombre(modificacion.getClase().getNombre());
        }

        if (modificacion.getInstructor() != null) {
            dto.setInstructorId(modificacion.getInstructor().getId());
            dto.setInstructorNombre(modificacion.getInstructor().getNombre());
        }

        if (modificacion.getModificadoPor() != null) {
            dto.setModificadoPorId(modificacion.getModificadoPor().getId());
            dto.setModificadoPorNombre(modificacion.getModificadoPor().getNombre());
        }

        dto.setTotalInscritos(modificacion.getClase() != null && modificacion.getClase().getInscripciones() != null ?
                modificacion.getClase().getInscripciones().size() : 0);

        return dto;
    }
}