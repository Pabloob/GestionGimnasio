package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.ClienteDTO;
import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.repository.ClienteRepository;
import com.gestiongimnasio.backend.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        // Crear el encriptador de contraseñas
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Hashear la contraseña
        String contrasenaHasheada = passwordEncoder.encode(clienteDTO.getContrasena());

        // Establecer la contraseña hasheada en el DTO
        clienteDTO.setContrasena(contrasenaHasheada);

        // Convertir DTO a entidad
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);

        // Guardar la entidad
        Cliente clienteGuardado = clienteRepository.save(cliente);

        // Convertir entidad a DTO
        return modelMapper.map(clienteGuardado, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Actualizar campos
        modelMapper.map(clienteDTO, clienteExistente);

        // Guardar cambios
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return modelMapper.map(clienteActualizado, ClienteDTO.class);
    }


    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteDTO incrementarInasistencias(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setInasistencias(cliente.getInasistencias() + 1);
        Cliente clienteActualizado = clienteRepository.save(cliente);
        return modelMapper.map(clienteActualizado, ClienteDTO.class);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return clienteRepository.existsByCorreo(correo);
    }

    @Override
    public Long authenticateCliente(String email, String password) {
        Cliente cliente = clienteRepository.getClienteByCorreoAndContrasena(email, password)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return cliente.getId();
    }

    @Override
    public ClienteDTO obtenerClientePorCorreo(String correo) {
        Cliente cliente = clienteRepository.getClienteByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}