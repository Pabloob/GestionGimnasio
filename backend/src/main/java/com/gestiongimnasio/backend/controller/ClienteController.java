package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.ClienteGetDTO;
import com.gestiongimnasio.backend.dto.post.ClientePostDTO;
import com.gestiongimnasio.backend.dto.put.ClientePutDTO;
import com.gestiongimnasio.backend.service.ClienteService;
import com.gestiongimnasio.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public ClienteController(ClienteService clienteService, UsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteGetDTO>> getAll() {
        List<ClienteGetDTO> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> getById(@PathVariable Long id) {
        ClienteGetDTO cliente = clienteService.getById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/clases/{id}")
    public ResponseEntity<List<ClaseGetDTO>> getClasesInscritas(@PathVariable Long id) {
        List<ClaseGetDTO> clases = clienteService.getClasesInscritas(id);

        return ResponseEntity.ok(clases);
    }

    @PostMapping
    public ResponseEntity<ClienteGetDTO> save(@RequestBody ClientePostDTO clienteDTO) {
        if (usuarioService.existsByCorreo(clienteDTO.getCorreo())) {
            return ResponseEntity.badRequest().build();
        }

        ClienteGetDTO nuevoCliente = clienteService.save(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteGetDTO> update(
            @PathVariable Long id,
            @RequestBody ClientePutDTO clienteDTO) {

        ClienteGetDTO updatedCliente = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(updatedCliente);
    }

    @PutMapping("/inasistencias/{id}")
    public ResponseEntity<ClienteGetDTO> incrementarInasistencias(@PathVariable Long id) {
        ClienteGetDTO cliente = clienteService.incrementarInasistencias(id);
        return ResponseEntity.ok(cliente);
    }

}