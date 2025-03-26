package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.InscripcionDTO;
import com.gestiongimnasio.backend.service.InscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<InscripcionDTO>> getAllInscripciones() {
        List<InscripcionDTO> inscripciones = inscripcionService.getAllInscripciones();
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> getInscripcionById(@PathVariable Integer id) {
        InscripcionDTO inscripcion = inscripcionService.getInscripcionById(id);
        return ResponseEntity.ok(inscripcion);
    }

    @PostMapping
    public ResponseEntity<InscripcionDTO> createInscripcion(@RequestBody InscripcionDTO inscripcionDTO) {
        InscripcionDTO nuevaInscripcion = inscripcionService.saveInscripcion(inscripcionDTO);
        return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> updateInscripcion(@PathVariable Integer id, @RequestBody InscripcionDTO inscripcionDTO) {
        InscripcionDTO updatedInscripcion = inscripcionService.updateInscripcion(id, inscripcionDTO);
        return ResponseEntity.ok(updatedInscripcion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable Integer id) {
        inscripcionService.deleteInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<InscripcionDTO>> getInscripcionesByCliente(@PathVariable Long clienteId) {
        List<InscripcionDTO> inscripciones = inscripcionService.getInscripcionesByCliente(clienteId);
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<InscripcionDTO>> getInscripcionesByClase(@PathVariable Integer claseId) {
        List<InscripcionDTO> inscripciones = inscripcionService.getInscripcionesByClase(claseId);
        return ResponseEntity.ok(inscripciones);
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<InscripcionDTO> marcarComoPagado(@PathVariable Integer id, @RequestParam boolean pagado) {
        InscripcionDTO inscripcion = inscripcionService.getInscripcionById(id);
        inscripcion.setEstadoPago(pagado);
        InscripcionDTO updatedInscripcion = inscripcionService.updateInscripcion(id, inscripcion);
        return ResponseEntity.ok(updatedInscripcion);
    }

}