package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.UsuarioLoginDTO;
import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;
import com.gestiongimnasio.backend.dto.put.UsuarioPutDTO;
import com.gestiongimnasio.backend.security.JwtTokenProvider;
import com.gestiongimnasio.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginDTO loginRequest) {
        try {
            // Autenticación
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreo(),
                            loginRequest.getContrasena()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generación del token
            String token = jwtTokenProvider.generateToken(authentication);

            // Obtención de detalles del usuario
            Object usuario = usuarioService.authenticate(loginRequest);

            // Construcción de la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");

            if (usuario instanceof ClienteGetDTO) {
                response.put("role", "CLIENTE");
                response.put("userDetails", usuario);
            } else if (usuario instanceof TrabajadorGetDTO trabajador) {
                response.put("role", trabajador.getTipoTrabajador().name());
                response.put("userDetails", usuario);
            }
            System.out.println("BIEN");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "Unauthorized",
                            "message", "Correo o contraseña incorrectos"
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Internal Server Error",
                            "message", "Ocurrió un error durante la autenticación"
                    ));
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioGetDTO>> getAll() {
        List<UsuarioGetDTO> usuarios = usuarioService.getAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetDTO> getById(@PathVariable Long id) {
        UsuarioGetDTO usuario = usuarioService.getById(id);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioGetDTO> getByCorreo(@PathVariable String correo) {
        UsuarioGetDTO usuario = usuarioService.getByCorreo(correo);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/existe/{correo}")
    public ResponseEntity<Boolean> existsByCorreo(@PathVariable String correo) {
        boolean existe = usuarioService.existsByCorreo(correo);

        return ResponseEntity.ok(existe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetDTO> update(
            @PathVariable Long id,
            @RequestBody UsuarioPutDTO clienteDTO) {

        UsuarioGetDTO updatedUsuario = usuarioService.update(id, clienteDTO);
        return ResponseEntity.ok(updatedUsuario);
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        usuarioService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
