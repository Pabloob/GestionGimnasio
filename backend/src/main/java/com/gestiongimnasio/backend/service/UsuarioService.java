package com.gestiongimnasio.backend.service;

public interface UsuarioService {

    Object authenticate(String correo, String contrasena);
}
