package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.InscripcionDTO;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.InscripcionRepository;
import com.gestiongimnasio.backend.service.InscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InscripcionDTO saveInscripcion(InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcion = modelMapper.map(inscripcionDTO, Inscripcion.class);

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        return modelMapper.map(inscripcionGuardada, InscripcionDTO.class);
    }

    @Override
    public List<InscripcionDTO> getAllInscripciones() {
        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcion -> modelMapper.map(inscripcion, InscripcionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionDTO getInscripcionById(Integer id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrado"));
        return modelMapper.map(inscripcion, InscripcionDTO.class);
    }

    @Override
    public InscripcionDTO updateInscripcion(Integer id, InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcionExistente = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrado"));

        // Actualizar campos
        modelMapper.map(inscripcionDTO, inscripcionExistente);

        // Guardar cambios
        Inscripcion inscripcionActualizado = inscripcionRepository.save(inscripcionExistente);
        return modelMapper.map(inscripcionActualizado, InscripcionDTO.class);
    }

    @Override
    public void deleteInscripcion(Integer id) {
        inscripcionRepository.deleteById(id);
    }

    @Override
    public List<InscripcionDTO> getInscripcionesByCliente(Long clienteId) {
        return inscripcionRepository.findByClienteId(clienteId)
                .stream()
                .map(inscripcion -> modelMapper.map(inscripcion, InscripcionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<InscripcionDTO> getInscripcionesByClase(Integer claseId) {
        return inscripcionRepository.findByClaseId(claseId)
                .stream()
                .map(inscripcion -> modelMapper.map(inscripcion, InscripcionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsInscripcion(Long clienteId, Integer claseId) {
        return inscripcionRepository.existsByClienteIdAndClaseId(clienteId, claseId);
    }

}