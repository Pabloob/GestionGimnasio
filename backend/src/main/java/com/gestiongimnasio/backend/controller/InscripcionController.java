package com.gestiongimnasio.backend.controller;

import java.util.List;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gestiongimnasio.backend.service.InscripcionService;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<InscripcionGetDTO>> getAll() {
        List<InscripcionGetDTO> inscripciones = inscripcionService.getAll();
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<InscripcionGetDTO>> getByCliente(@PathVariable Long clienteId) {
        List<InscripcionGetDTO> inscripciones = inscripcionService.getByCliente(clienteId);
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<InscripcionGetDTO>> getByClase(@PathVariable Long claseId) {
        List<InscripcionGetDTO> inscripciones = inscripcionService.getByClase(claseId);
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionGetDTO> getById(@PathVariable Long id) {
        InscripcionGetDTO inscripcion = inscripcionService.getById(id);
        return ResponseEntity.ok(inscripcion);
    }

    @PostMapping
    public ResponseEntity<InscripcionGetDTO> save(@RequestBody InscripcionPostDTO inscripcionDTO) {
        InscripcionGetDTO nuevaInscripcion = inscripcionService.save(inscripcionDTO);
        return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionGetDTO> update(@PathVariable Long id, @RequestBody InscripcionPutDTO inscripcionDTO) {
        InscripcionGetDTO updatedInscripcion = inscripcionService.update(id, inscripcionDTO);
        return ResponseEntity.ok(updatedInscripcion);
    }

    @PutMapping("/pago/{id}")
    public ResponseEntity<InscripcionGetDTO> confirmarPago(@PathVariable Long id, @RequestParam Long idPago) {
        InscripcionGetDTO inscripcion = inscripcionService.confirmarPago(id, idPago);
        return ResponseEntity.ok(inscripcion);
    }

    @PutMapping("/asistencia/{id}")
    public ResponseEntity<InscripcionGetDTO> confirmarAsistencia(@PathVariable Long id) {
        InscripcionGetDTO inscripcion = inscripcionService.confirmarAsistencia(id);
        return ResponseEntity.ok(inscripcion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscripcionService.delete(id);
        return ResponseEntity.noContent().build();
    }


}