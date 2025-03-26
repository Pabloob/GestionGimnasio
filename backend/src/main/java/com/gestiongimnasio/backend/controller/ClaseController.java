package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.ClaseDTO;
import com.gestiongimnasio.backend.service.ClaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping
    public ResponseEntity<List<ClaseDTO>> getAllClases() {
        List<ClaseDTO> clases = claseService.getAllClases();
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseDTO> getClaseById(@PathVariable Integer id) {
        ClaseDTO clase = claseService.getClaseById(id);
        return ResponseEntity.ok(clase);
    }

    @PostMapping
    public ResponseEntity<ClaseDTO> createClase(@RequestBody ClaseDTO claseDTO) {
        ClaseDTO nuevaClase = claseService.saveClase(claseDTO);
        return new ResponseEntity<>(nuevaClase, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseDTO> updateClase(@PathVariable Integer id, @RequestBody ClaseDTO claseDTO) {
        ClaseDTO updatedClase = claseService.updateClase(id, claseDTO);
        return ResponseEntity.ok(updatedClase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClase(@PathVariable Integer id) {
        claseService.deleteClase(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ClaseDTO>> getClasesByInstructor(@PathVariable Long instructorId) {
        List<ClaseDTO> clases = claseService.getClasesByInstructor(instructorId);
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/search/{nombre}")
    public ResponseEntity<List<ClaseDTO>> searchClasesByNombre(@PathVariable String nombre) {
        List<ClaseDTO> clases = claseService.searchByNombre(nombre);
        return ResponseEntity.ok(clases);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ClaseDTO> cambiarEstadoClase(@PathVariable Integer id, @RequestParam boolean activa) {
        ClaseDTO clase = claseService.getClaseById(id);
        clase.setActiva(activa);
        ClaseDTO updatedClase = claseService.updateClase(id, clase);
        return ResponseEntity.ok(updatedClase);
    }
}