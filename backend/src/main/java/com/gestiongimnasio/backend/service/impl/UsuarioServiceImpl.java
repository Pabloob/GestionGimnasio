package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.UsuarioLoginDTO;
import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;
import com.gestiongimnasio.backend.dto.post.UsuarioPostDTO;
import com.gestiongimnasio.backend.dto.put.UsuarioPutDTO;
import com.gestiongimnasio.backend.model.Usuario;
import com.gestiongimnasio.backend.repository.UsuarioRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.service.TrabajadorService;
import com.gestiongimnasio.backend.service.UsuarioService;
import com.gestiongimnasio.backend.utils.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final ClienteService clienteService;
    private final TrabajadorService trabajadorService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              ModelMapper modelMapper,
                              ClienteService clienteService,
                              TrabajadorService trabajadorService,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.clienteService = clienteService;
        this.trabajadorService = trabajadorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Object authenticate(UsuarioLoginDTO loginDTO) {
        if (loginDTO.getCorreo() == null || loginDTO.getContrasena() == null) {
            throw new IllegalArgumentException("Correo y contraseña son requeridos");
        }

        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas")); // Mensaje genérico por seguridad

        if (!passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena())) {
            throw new BusinessException("Credenciales inválidas");
        }

        if (!usuario.isActivo()) {
            throw new BusinessException("Usuario inactivo");
        }

        return switch (usuario.getTipoUsuario()) {
            case CLIENTE -> clienteService.getById(usuario.getId());
            case TRABAJADOR -> trabajadorService.getById(usuario.getId());
        };
    }

    @Transactional
    @Override
    public UsuarioGetDTO save(UsuarioPostDTO registroDTO) {
        if (registroDTO == null) {
            throw new IllegalArgumentException("Datos de registro no pueden ser nulos");
        }

        if (usuarioRepository.existsByCorreo(registroDTO.getCorreo())) {
            throw new BusinessException("El correo ya está registrado");
        }

        Usuario usuario = modelMapper.map(registroDTO, Usuario.class);
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuario.setActivo(true);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return toResponseDTO(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioGetDTO getById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id));
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioGetDTO getByCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Correo no puede estar vacío");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con correo: " + correo));
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioGetDTO> getAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UsuarioGetDTO update(Long id, UsuarioPutDTO updateDTO) {
        if (updateDTO == null || !id.equals(updateDTO.getId())) {
            throw new IllegalArgumentException("Datos de actualización inválidos");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updateDTO, usuario);

        if (updateDTO.getContrasena() != null && !updateDTO.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(updateDTO.getContrasena()));
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return toResponseDTO(updatedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Transactional
    @Override
    public void deactivate(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + id));

        if (!usuario.isActivo()) {
            throw new BusinessException("El usuario ya está inactivo");
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioGetDTO toResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioGetDTO.class);
    }
}