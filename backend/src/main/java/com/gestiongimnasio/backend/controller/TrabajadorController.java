package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.LoginDTO;
import com.gestiongimnasio.backend.dto.TrabajadorDTO;
import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.security.JwtTokenProvider;
import com.gestiongimnasio.backend.service.TrabajadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;


    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TrabajadorDTO>> getAllTrabajadores() {
        List<TrabajadorDTO> trabajadores = trabajadorService.getAllTrabajadores();
        return ResponseEntity.ok(trabajadores);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<TrabajadorDTO> getTrabajadorById(@PathVariable Long id) {
        TrabajadorDTO trabajador = trabajadorService.getTrabajadorById(id);
        return ResponseEntity.ok(trabajador);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrabajadorDTO> createTrabajador(@RequestBody TrabajadorDTO trabajadorDTO) {
        if(trabajadorService.existsByCorreo(trabajadorDTO.getCorreo())) {
            return ResponseEntity.badRequest().build();
        }

        TrabajadorDTO nuevoTrabajador = trabajadorService.saveTrabajador(trabajadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTrabajador);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrabajadorDTO> updateTrabajador(@PathVariable Long id, @RequestBody TrabajadorDTO trabajadorDTO) {
        TrabajadorDTO updatedTrabajador = trabajadorService.updateTrabajador(id, trabajadorDTO);
        return ResponseEntity.ok(updatedTrabajador);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        trabajadorService.deleteTrabajador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TrabajadorDTO>> getTrabajadoresByTipo(@PathVariable Trabajador.TipoTrabajador tipo) {
        List<TrabajadorDTO> trabajadores = trabajadorService.findByTipo(tipo);
        return ResponseEntity.ok(trabajadores);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Long> authenticateTrabajador(@RequestBody LoginDTO loginDTO) {
        Long id = trabajadorService.authenticateCliente(loginDTO.getCorreo(), loginDTO.getContrasena());
        return ResponseEntity.ok(id);
    }
}