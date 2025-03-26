package com.gestiongimnasio.backend.security;

import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.model.Usuario;
import com.gestiongimnasio.backend.repository.ClienteRepository;
import com.gestiongimnasio.backend.repository.TrabajadorRepository;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TrabajadorRepository trabajadorRepository;
    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(TrabajadorRepository trabajadorRepository,
                                    ClienteRepository clienteRepository) {
        this.trabajadorRepository = trabajadorRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Trabajador trabajador = trabajadorRepository.findByCorreo(correo).orElse(null);

        if (trabajador != null) {
            return new UserWithId(
                    trabajador.getCorreo(),
                    trabajador.getContrasena(),
                    getAuthorities(trabajador),
                    trabajador.getId()
            );
        }

        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UserWithId(
                cliente.getCorreo(),
                cliente.getContrasena(),
                getAuthorities(cliente),
                cliente.getId()
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        if (usuario instanceof Trabajador t) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + t.getTipoTrabajador().name()));
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Getter
    public static class UserWithId extends User {
        private final Long userId;

        public UserWithId(String username, String password,
                          Collection<? extends GrantedAuthority> authorities,
                          Long userId) {
            super(username, password, authorities);
            this.userId = userId;
        }
    }
}