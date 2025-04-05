package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;
import com.gestiongimnasio.backend.mappers.ClienteMapper;
import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Usuario;
import com.gestiongimnasio.backend.repository.ClienteRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClienteGetDTO> findAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toClienteGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteGetDTO findClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toClienteGetDTO(cliente);
    }

    @Override
    public ClienteGetDTO saveCliente(ClientePostDTO clientePostDTO) {

        // Validar que el correo no esté duplicado
        if (clienteRepository.existsByCorreo(clientePostDTO.getUsuarioPostDTO().getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Encriptar la contraseña antes de guardar
        clientePostDTO.getUsuarioPostDTO().setContrasena(passwordEncoder.encode(clientePostDTO.getUsuarioPostDTO().getContrasena()));

        System.out.println("Antes de mapeo" + clientePostDTO.getUsuarioPostDTO());
        Cliente cliente = clienteMapper.fromClientePostDTO(clientePostDTO);
        System.out.println("Despues de mapeo" + cliente);


        cliente.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);

        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toClienteGetDTO(savedCliente);
    }

    @Override
    public ClienteGetDTO updateCliente(Long id, ClientePutDTO clientePutDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        // Validar que el nuevo correo no esté duplicado (si se proporciona un correo diferente)
        if (clientePutDTO.getUsuarioPutDTO().getCorreo() != null &&
                !clientePutDTO.getUsuarioPutDTO().getCorreo().equals(cliente.getCorreo()) &&
                clienteRepository.existsByCorreo(clientePutDTO.getUsuarioPutDTO().getCorreo())) {
            throw new RuntimeException("El nuevo correo ya está registrado");
        }

        // Actualizar contraseña solo si se proporciona una nueva
        if (clientePutDTO.getUsuarioPutDTO().getContrasena() != null && !clientePutDTO.getUsuarioPutDTO().getContrasena().isEmpty()) {
            clientePutDTO.getUsuarioPutDTO().setContrasena(passwordEncoder.encode(clientePutDTO.getUsuarioPutDTO().getContrasena()));
        }

        System.out.println("Antes de mapeo" + clientePutDTO);
        clienteMapper.fromClientePutDTO(clientePutDTO, cliente);
        System.out.println("Despues de mapeo" + cliente);

        Cliente updatedCliente = clienteRepository.save(cliente);
        return clienteMapper.toClienteGetDTO(updatedCliente);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        clienteRepository.delete(cliente);
    }
}