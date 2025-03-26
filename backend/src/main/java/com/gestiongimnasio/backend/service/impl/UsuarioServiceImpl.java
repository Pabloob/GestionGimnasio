package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.model.Usuario;
import com.gestiongimnasio.backend.repository.UsuarioRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.service.TrabajadorService;
import com.gestiongimnasio.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private TrabajadorService trabajadorService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Object authenticate(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.getUsuarioByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña usando el passwordEncoder
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        // Devolver el usuario según su tipo
        if (usuario.getTipoUsuario() == Usuario.TipoUsuario.CLIENTE) {
            return clienteService.getClienteById(usuario.getId());
        } else {
            return trabajadorService.getTrabajadorById(usuario.getId());
        }
    }
}
