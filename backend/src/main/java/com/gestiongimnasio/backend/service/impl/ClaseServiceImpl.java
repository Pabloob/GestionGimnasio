package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;
import com.gestiongimnasio.backend.mappers.ClaseMapper;
import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.repository.ClaseRepository;
import com.gestiongimnasio.backend.repository.InscripcionRepository;
import com.gestiongimnasio.backend.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final ClaseRepository claseRepository;
    private final ClaseMapper claseMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<ClaseGetDTO> findAllClases() {
        System.out.println(claseRepository.findAll());
        return claseRepository.findAll()
                .stream()
                .map(claseMapper::toClaseGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaseGetDTO> findClasesActivas() {
        return claseRepository.findByActivaTrue()
                .stream()
                .map(claseMapper::toClaseGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaseGetDTO findClaseById(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        return claseMapper.toClaseGetDto(clase);
    }

    @Override
    public ClaseGetDTO saveClase(ClasePostDTO clasePostDTO) {
        Clase clase = claseMapper.fromClasePostDTO(clasePostDTO);
        Clase savedClase = claseRepository.save(clase);
        return claseMapper.toClaseGetDto(savedClase);
    }

    @Override
    public ClaseGetDTO updateClase(Long id, ClasePutDTO clasePutDTO) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));

        claseMapper.fromClasePutDTO(clasePutDTO, clase);
        Clase updatedClase = claseRepository.save(clase);
        return claseMapper.toClaseGetDto(updatedClase);
    }

    @Override
    public void toggleClaseStatus(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        clase.setActiva(!clase.isActiva());
        claseRepository.save(clase);
    }

    @Override
    public boolean tieneCuposDisponibles(Long claseId) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));

        return inscripcionRepository.findByClaseId(clase.getId()).size() < clase.getCapacidadMaxima();
    }

    @Override
    public int cuposDisponibles(Long claseId) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));
        return clase.getCapacidadMaxima() - inscripcionRepository.findByClaseId(clase.getId()).size();
    }

    @Override
    public void deleteClase(Long id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }
        claseRepository.deleteById(id);
    }
}