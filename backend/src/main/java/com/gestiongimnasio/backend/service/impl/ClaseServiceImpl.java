package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.ClaseDTO;
import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.repository.ClaseRepository;
import com.gestiongimnasio.backend.service.ClaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClaseDTO saveClase(ClaseDTO claseDTO) {
        Clase clase = modelMapper.map(claseDTO, Clase.class);

        Clase claseGuardada = claseRepository.save(clase);

        return modelMapper.map(claseGuardada, ClaseDTO.class);

    }

    @Override
    public List<ClaseDTO> getAllClases() {
        return claseRepository.findAll()
                .stream()
                .map(clase -> modelMapper.map(clase, ClaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClaseDTO getClaseById(Integer id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrado"));
        return modelMapper.map(clase, ClaseDTO.class);
    }

    @Override
    public ClaseDTO updateClase(Integer id, ClaseDTO claseDTO) {
        Clase claseExistente = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrado"));

        // Actualizar campos
        modelMapper.map(claseDTO, claseExistente);

        // Guardar cambios
        Clase claseActualizada = claseRepository.save(claseExistente);
        return modelMapper.map(claseActualizada, ClaseDTO.class);

    }

    @Override
    public void deleteClase(Integer id) {
        claseRepository.deleteById(id);
    }

    @Override
    public List<ClaseDTO> getClasesByInstructor(Long instructorId) {
        return claseRepository.findByInstructorId(instructorId)
                .stream()
                .map(clase -> modelMapper.map(clase, ClaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaseDTO> searchByNombre(String nombre) {
        return claseRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(clase -> modelMapper.map(clase, ClaseDTO.class))
                .collect(Collectors.toList());
    }
}