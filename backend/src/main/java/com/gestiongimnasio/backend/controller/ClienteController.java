package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;
import com.gestiongimnasio.backend.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteGetDTO>> getAllClientes() {
        List<ClienteGetDTO> clientes = clienteService.findAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> getClienteById(@PathVariable Long id) {
        ClienteGetDTO cliente = clienteService.findClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteGetDTO> createCliente(@RequestBody ClientePostDTO clientePostDTO) {
        ClienteGetDTO nuevoCliente = clienteService.saveCliente(clientePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> updateCliente(@PathVariable Long id,
                                                       @RequestBody ClientePutDTO clientePutDTO) {
        ClienteGetDTO clienteActualizado = clienteService.updateCliente(id, clientePutDTO);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}