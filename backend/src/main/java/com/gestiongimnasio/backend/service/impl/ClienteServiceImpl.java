package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;
import com.gestiongimnasio.backend.model.Clase;
import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Inscripcion;
import com.gestiongimnasio.backend.repository.ClienteRepository;
import com.gestiongimnasio.backend.repository.UsuarioRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.utils.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ClienteGetDTO getById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + id));
        return toResponseDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteGetDTO> getAll() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseGetDTO> getClasesInscritas(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new BusinessException("Cliente no encontrado con ID: " + clienteId);
        }

        List<Inscripcion> inscripciones = clienteRepository.findInscripcionesByClienteIdWithClase(clienteId);
        return inscripciones.stream()
                .map(Inscripcion::getClase)
                .map(this::toClaseResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteGetDTO save(ClientePostDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("Datos del cliente no pueden ser nulos");
        }

        if (usuarioRepository.existsByCorreo(clienteDTO.getCorreo())) {
            throw new BusinessException("El correo ya está registrado");
        }

        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        cliente.setContrasena(passwordEncoder.encode(clienteDTO.getContrasena()));
        cliente.setActivo(true);
        cliente.setInasistencias(0);

        Cliente clienteGuardado = clienteRepository.save(cliente);
        return toResponseDTO(clienteGuardado);
    }

    @Override
    public ClienteGetDTO update(Long id, ClientePutDTO clienteDTO) {
        if (!id.equals(clienteDTO.getId())) {
            throw new IllegalArgumentException("ID de cliente no coincide");
        }

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + id));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(clienteDTO, clienteExistente);

        // Actualizar contraseña si se proporciona
        if (clienteDTO.getContrasena() != null && !clienteDTO.getContrasena().isBlank()) {
            clienteExistente.setContrasena(passwordEncoder.encode(clienteDTO.getContrasena()));
        }

        return toResponseDTO(clienteRepository.save(clienteExistente));
    }

    @Override
    public ClienteGetDTO incrementarInasistencias(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + id));

        cliente.setInasistencias(cliente.getInasistencias() + 1);
        return toResponseDTO(clienteRepository.save(cliente));
    }

    private ClienteGetDTO toResponseDTO(Cliente cliente) {
        ClienteGetDTO dto = modelMapper.map(cliente, ClienteGetDTO.class);

        // Mapeo seguro de relaciones
        if (cliente.getInscripciones() != null) {
            dto.setInscripcionesIds(
                    cliente.getInscripciones().stream()
                            .map(Inscripcion::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private ClaseGetDTO toClaseResponseDTO(Clase clase) {
        return modelMapper.map(clase, ClaseGetDTO.class);
    }
}