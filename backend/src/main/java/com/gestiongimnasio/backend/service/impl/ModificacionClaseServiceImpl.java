package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.ModificacionClaseDTO;
import com.gestiongimnasio.backend.model.ModificacionClase;
import com.gestiongimnasio.backend.repository.ModificacionClaseRepository;
import com.gestiongimnasio.backend.service.ModificacionClaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModificacionClaseServiceImpl implements ModificacionClaseService {

    @Autowired
    private ModificacionClaseRepository modificacionClaseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ModificacionClaseDTO saveModificacion(ModificacionClaseDTO modificacionClaseDTO) {
        ModificacionClase modificacionClase = modelMapper.map(modificacionClaseDTO, ModificacionClase.class);

        ModificacionClase modificacionClaseGuardada = modificacionClaseRepository.save(modificacionClase);

        return modelMapper.map(modificacionClaseGuardada, ModificacionClaseDTO.class);
    }

    @Override
    public List<ModificacionClaseDTO> getAllModificaciones() {
        return modificacionClaseRepository.findAll()
                .stream()
                .map(modificacionClase -> modelMapper.map(modificacionClase, ModificacionClaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ModificacionClaseDTO getModificacionById(Integer id) {
        ModificacionClase modificacionClase = modificacionClaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modificacion de clase no encontrado"));
        return modelMapper.map(modificacionClase, ModificacionClaseDTO.class);
    }

    @Override
    public List<ModificacionClaseDTO> getModificacionesByClase(Integer claseId) {
        return modificacionClaseRepository.findByClaseId(claseId)
                .stream()
                .map(modificacionClase -> modelMapper.map(modificacionClase, ModificacionClaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ModificacionClaseDTO> getModificacionesByInstructor(Long instructorId) {
        return modificacionClaseRepository.findByInstructorId(instructorId)
                .stream()
                .map(modificacionClase -> modelMapper.map(modificacionClase, ModificacionClaseDTO.class))
                .collect(Collectors.toList());
    }
}