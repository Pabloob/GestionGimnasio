package com.gestiongimnasio.backend.security;

import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.repository.ClienteRepository;
import com.gestiongimnasio.backend.repository.TrabajadorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String CLIENTE_ROLE = "CLIENTE";

    private final TrabajadorRepository trabajadorRepository;
    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(TrabajadorRepository trabajadorRepository,
                                    ClienteRepository clienteRepository) {
        this.trabajadorRepository = trabajadorRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByCorreo(correo);

        if (trabajadorOpt.isPresent()) {
            Trabajador trabajador = trabajadorOpt.get();
            return buildUserDetails(
                    trabajador.getCorreo(),
                    trabajador.getContrasena(),
                    getTrabajadorAuthorities(trabajador)
            );
        }

        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        return buildUserDetails(
                cliente.getCorreo(),
                cliente.getContrasena(),
                getClienteAuthorities()
        );
    }

    private Collection<? extends GrantedAuthority> getTrabajadorAuthorities(Trabajador trabajador) {
        String role = trabajador.getTipoTrabajador() != null ?
                trabajador.getTipoTrabajador().name() :
                "TRABAJADOR";
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }

    private Collection<? extends GrantedAuthority> getClienteAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + CLIENTE_ROLE));
    }

    private UserDetails buildUserDetails(String username, String password,
                                         Collection<? extends GrantedAuthority> authorities) {
        return User.builder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}