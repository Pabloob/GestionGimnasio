package com.gestiongimnasio.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Obtener el token de la cabecera Authorization
        String authHeader = request.getHeader("Authorization");

        // Verificar si el token está presente
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return false;
        }

        // Extraer el token
        String token = authHeader.substring(7);

        // Validar el token
        if (!jwtTokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return false;
        }

        // Obtener el ID del usuario del token
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        // Asignar el ID del usuario a la solicitud
        request.setAttribute("userId", userId);

        return true;
    }
}