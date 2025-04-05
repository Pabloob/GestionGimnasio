package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.UsuarioLoginDTO;
import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;
import com.gestiongimnasio.backend.mappers.UsuarioMapper;
import com.gestiongimnasio.backend.model.Usuario;
import com.gestiongimnasio.backend.repository.UsuarioRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.service.TrabajadorService;
import com.gestiongimnasio.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteService clienteService;
    private final TrabajadorService trabajadorService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Object authenticate(UsuarioLoginDTO loginDTO) {
        if (loginDTO.getCorreo() == null || loginDTO.getContrasena() == null) {
            throw new IllegalArgumentException("Correo y contraseña son requeridos");
        }

        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return switch (usuario.getTipoUsuario()) {
            case CLIENTE -> clienteService.findClienteById(usuario.getId());
            case TRABAJADOR -> trabajadorService.findTrabajadorById(usuario.getId());
        };
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioGetDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toUsuarioGetDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioGetDTO getByCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Correo no puede estar vacío");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
        return usuarioMapper.toUsuarioGetDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioGetDTO> getAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toUsuarioGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public void toggleUsuarioStatus(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        boolean nuevoEstado = !usuario.isActivo();
        usuario.setActivo(nuevoEstado);
        usuarioRepository.save(usuario);

        System.out.println("El estado del usuario con ID " + id + " ha sido cambiado a " + (nuevoEstado ? "activo" : "inactivo"));
    }


}