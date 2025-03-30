package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.repository.TrabajadorRepository;
import com.gestiongimnasio.backend.service.TrabajadorService;
import com.gestiongimnasio.backend.utils.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public TrabajadorGetDTO getById(Long id) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Trabajador no encontrado con ID: " + id));
        return toResponseDTO(trabajador);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajadorGetDTO> getByTipo(Trabajador.TipoTrabajador tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de trabajador no puede ser nulo");
        }

        return trabajadorRepository.findByTipoTrabajador(tipo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajadorGetDTO> getAll() {
        return trabajadorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseGetDTO> getClasesImpartidas(Long id) {
        Trabajador trabajador = trabajadorRepository.findByIdWithClases(id)
                .orElseThrow(() -> new BusinessException("Trabajador no encontrado con ID: " + id));

        if (trabajador.getTipoTrabajador() != Trabajador.TipoTrabajador.INSTRUCTOR) {
            throw new BusinessException("Solo los instructores pueden impartir clases");
        }

        return trabajador.getClases().stream()
                .map(this::toClaseResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrabajadorGetDTO save(TrabajadorPostDTO trabajadorDTO) {
        if (trabajadorDTO == null) {
            throw new IllegalArgumentException("Datos del trabajador no pueden ser nulos");
        }

        if (trabajadorRepository.existsByCorreo(trabajadorDTO.getCorreo())) {
            throw new BusinessException("El correo ya está registrado");
        }

        Trabajador trabajador = modelMapper.map(trabajadorDTO, Trabajador.class);
        trabajador.setContrasena(passwordEncoder.encode(trabajadorDTO.getContrasena()));
        trabajador.setActivo(true);

        Trabajador trabajadorGuardado = trabajadorRepository.save(trabajador);
        return toResponseDTO(trabajadorGuardado);
    }

    @Override
    public TrabajadorGetDTO update(Long id, TrabajadorPutDTO trabajadorDTO) {
        if (!id.equals(trabajadorDTO.getId())) {
            throw new IllegalArgumentException("ID de trabajador no coincide");
        }

        Trabajador trabajadorExistente = trabajadorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Trabajador no encontrado con ID: " + id));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(trabajadorDTO, trabajadorExistente);

        if (trabajadorDTO.getContrasena() != null && !trabajadorDTO.getContrasena().isBlank()) {
            trabajadorExistente.setContrasena(passwordEncoder.encode(trabajadorDTO.getContrasena()));
        }

        return toResponseDTO(trabajadorRepository.save(trabajadorExistente));
    }

    private TrabajadorGetDTO toResponseDTO(Trabajador trabajador) {
        TrabajadorGetDTO dto = modelMapper.map(trabajador, TrabajadorGetDTO.class);

        if (trabajador.getClases() != null) {
            dto.setClasesIds(
                    trabajador.getClases().stream()
                            .map(Clase::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private ClaseGetDTO toClaseResponseDTO(Clase clase) {
        return modelMapper.map(clase, ClaseGetDTO.class);
    }
}