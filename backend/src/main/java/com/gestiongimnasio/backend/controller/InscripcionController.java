package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;
import com.gestiongimnasio.backend.service.InscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<InscripcionGetDTO>> getAllInscripciones() {
        List<InscripcionGetDTO> inscripciones = inscripcionService.findAllInscripciones();
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionGetDTO> getInscripcionById(@PathVariable Long id) {
        InscripcionGetDTO inscripcion = inscripcionService.findInscripcionById(id);
        return ResponseEntity.ok(inscripcion);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<InscripcionGetDTO>> getInscripcionesByCliente(@PathVariable Long clienteId) {
        List<InscripcionGetDTO> inscripciones = inscripcionService.findByCliente(clienteId);
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<InscripcionGetDTO>> getInscripcionesByClase(@PathVariable Long claseId) {
        List<InscripcionGetDTO> inscripciones = inscripcionService.findByClase(claseId);
        return ResponseEntity.ok(inscripciones);
    }

    @PostMapping
    public ResponseEntity<InscripcionGetDTO> createInscripcion(@RequestBody InscripcionPostDTO inscripcionPostDTO) {
        InscripcionGetDTO nuevaInscripcion = inscripcionService.saveInscripcion(inscripcionPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionGetDTO> updateInscripcion(@PathVariable Long id,
                                                               @RequestBody InscripcionPutDTO inscripcionPutDTO) {
        InscripcionGetDTO inscripcionActualizada = inscripcionService.updateInscripcion(id, inscripcionPutDTO);
        return ResponseEntity.ok(inscripcionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable Long id) {
        inscripcionService.deleteInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/registrar-asistencia")
    public ResponseEntity<Void> registrarAsistencia(@PathVariable Long id) {
        inscripcionService.registrarAsistencia(id);
        return ResponseEntity.noContent().build();
    }
}