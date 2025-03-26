package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.ClienteDTO;
import com.gestiongimnasio.backend.dto.LoginDTO;
import com.gestiongimnasio.backend.security.JwtTokenProvider;
import com.gestiongimnasio.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/authentication")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginDTO loginDTO) {

        try {
            // Intentamos autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getCorreo(),
                            loginDTO.getContrasena()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);

            Object usuario = usuarioService.authenticate(loginDTO.getCorreo(), loginDTO.getContrasena());

            if (usuario instanceof ClienteDTO) {
                response.put("cliente", usuario);
            } else {
                response.put("trabajador", usuario);
            }


            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales incorrectas"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error en el servidor"));
        }
    }
}
