package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.SalaGetDTO;
import com.gestiongimnasio.backend.dto.post.SalaPostDTO;
import com.gestiongimnasio.backend.dto.put.SalaPutDTO;
import com.gestiongimnasio.backend.mappers.SalaMapper;
import com.gestiongimnasio.backend.model.Sala;
import com.gestiongimnasio.backend.repository.ClaseHorarioRepository;
import com.gestiongimnasio.backend.repository.SalaRepository;
import com.gestiongimnasio.backend.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SalaServiceImpl implements SalaService {

    private final SalaRepository salaRepository;
    private final ClaseHorarioRepository claseHorarioRepository;
    private final SalaMapper salaMapper;

    @Override
    public List<SalaGetDTO> findAllSalas() {
        return salaRepository.findAll()
                .stream()
                .map(salaMapper::toSalaGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalaGetDTO findSalaById(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + id));
        return salaMapper.toSalaGetDTO(sala);
    }

    @Override
    public SalaGetDTO saveSala(SalaPostDTO salaPostDTO) {
        // Validar nombre único
        if (salaRepository.existsByNombre(salaPostDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una sala con ese nombre");
        }

        Sala sala = salaMapper.fromSalaPostDTO(salaPostDTO);
        Sala savedSala = salaRepository.save(sala);
        return salaMapper.toSalaGetDTO(savedSala);
    }

    @Override
    public SalaGetDTO updateSala(Long id, SalaPutDTO salaPutDTO) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + id));

        // Validar nombre único si se está cambiando
        if (salaPutDTO.getNombre() != null &&
                !salaPutDTO.getNombre().equals(sala.getNombre()) &&
                salaRepository.existsByNombre(salaPutDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una sala con ese nombre");
        }

        salaMapper.fromSalaPutDTO(salaPutDTO, sala);
        Sala updatedSala = salaRepository.save(sala);
        return salaMapper.toSalaGetDTO(updatedSala);
    }

    @Override
    public void deleteSala(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala no encontrada con ID: " + id);
        }

        // Validar que no tenga reservas asociadas
        if (claseHorarioRepository.existsBySalaId(id)) {
            throw new RuntimeException("No se puede eliminar una sala con reservas asociadas");
        }

        salaRepository.deleteById(id);
    }
}