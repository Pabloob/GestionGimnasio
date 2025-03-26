package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.ClienteDTO;
import com.gestiongimnasio.backend.dto.LoginDTO;
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
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        if (clienteService.existsByCorreo(clienteDTO.getCorreo())) {
            return ResponseEntity.badRequest().build();
        }

        ClienteDTO nuevoCliente = clienteService.saveCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(
            @PathVariable Long id,
            @RequestBody ClienteDTO clienteDTO) {

        ClienteDTO updatedCliente = clienteService.updateCliente(id, clienteDTO);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/inasistencias")
    public ResponseEntity<ClienteDTO> incrementarInasistencias(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.incrementarInasistencias(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Long> authenticateCliente(@RequestBody LoginDTO loginDTO) {
        Long id = clienteService.authenticateCliente(loginDTO.getCorreo(), loginDTO.getContrasena());
        return ResponseEntity.ok(id);
    }

}