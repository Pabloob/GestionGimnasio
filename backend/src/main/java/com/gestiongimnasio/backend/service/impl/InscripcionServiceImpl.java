package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;
import com.gestiongimnasio.backend.mappers.InscripcionMapper;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.ClaseService;
import com.gestiongimnasio.backend.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final ClaseRepository claseRepository;
    private final InscripcionMapper inscripcionMapper;
    private final ClaseService claseService;

    @Override
    public List<InscripcionGetDTO> findAllInscripciones() {
        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcionMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionGetDTO findInscripcionById(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
        return inscripcionMapper.toGetDto(inscripcion);
    }

    @Override
    public InscripcionGetDTO saveInscripcion(InscripcionPostDTO inscripcionPostDTO) {
        // Validar existencia de las entidades relacionadas
        Cliente cliente = clienteRepository.findById(inscripcionPostDTO.getClienteId()).orElseThrow();
        Clase clase = claseRepository.findById(inscripcionPostDTO.getClaseId()).orElseThrow();
        // Validar que la clase esté activa
        if (!clase.isActiva()) {
            throw new RuntimeException("No se puede inscribir a una clase inactiva");
        }

        // Validar cupos disponibles
        if (!claseService.tieneCuposDisponibles(clase.getId())) {
            throw new RuntimeException("La clase no tiene cupos disponibles");
        }

        // Validar que el cliente no esté ya inscrito en la clase
        if (inscripcionRepository.existsByClienteIdAndClaseId(cliente.getId(), clase.getId())) {
            throw new RuntimeException("El cliente ya está inscrito en esta clase");
        }

        // Crear la inscripción
        Inscripcion inscripcion = inscripcionMapper.toEntity(inscripcionPostDTO);
        inscripcion.setCliente(cliente);
        inscripcion.setClase(clase);

        Inscripcion savedInscripcion = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.toGetDto(savedInscripcion);
    }

    @Override
    public InscripcionGetDTO updateInscripcion(Long id, InscripcionPutDTO inscripcionPutDTO) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));

        // Validar entidades relacionadas si se están actualizando
        if (inscripcionPutDTO.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(inscripcionPutDTO.getClienteId()).orElseThrow();
            inscripcion.setCliente(cliente);

        }
        if (inscripcionPutDTO.getClaseId() != null) {
            Clase clase = claseRepository.findById(inscripcionPutDTO.getClaseId()).orElseThrow();
            inscripcion.setClase(clase);
        }

        inscripcionMapper.updateFromDto(inscripcionPutDTO, inscripcion);


        Inscripcion updatedInscripcion = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.toGetDto(updatedInscripcion);
    }

    @Override
    public void deleteInscripcion(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + id);
        }
        inscripcionRepository.deleteById(id);
    }

    @Override
    public List<InscripcionGetDTO> findByCliente(Long clienteId) {
        return inscripcionRepository.findByClienteId(clienteId)
                .stream()
                .map(inscripcionMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InscripcionGetDTO> findByClase(Long claseId) {
        return inscripcionRepository.findByClaseId(claseId)
                .stream()
                .map(inscripcionMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarAsistencia(Long inscripcionId) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + inscripcionId));

        inscripcion.setAsistio(true);
        inscripcionRepository.save(inscripcion);
    }

}