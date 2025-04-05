package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;
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
    public ResponseEntity<List<ClaseGetDTO>> getAllClases() {
        List<ClaseGetDTO> clases = claseService.findAllClases();
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<ClaseGetDTO>> getClasesActivas() {
        List<ClaseGetDTO> clasesActivas = claseService.findClasesActivas();
        return ResponseEntity.ok(clasesActivas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseGetDTO> getClaseById(@PathVariable Long id) {
        ClaseGetDTO clase = claseService.findClaseById(id);
        return ResponseEntity.ok(clase);
    }

    @GetMapping("/{id}/cupos-disponibles")
    public ResponseEntity<Integer> getCuposDisponibles(@PathVariable Long id) {
        int cupos = claseService.cuposDisponibles(id);
        return ResponseEntity.ok(cupos);
    }

    @GetMapping("/{id}/tiene-cupos")
    public ResponseEntity<Boolean> tieneCuposDisponibles(@PathVariable Long id) {
        boolean tieneCupos = claseService.tieneCuposDisponibles(id);
        return ResponseEntity.ok(tieneCupos);
    }

    @PostMapping
    public ResponseEntity<ClaseGetDTO> createClase(@RequestBody ClasePostDTO clasePostDTO) {
        ClaseGetDTO nuevaClase = claseService.saveClase(clasePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaClase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseGetDTO> updateClase(@PathVariable Long id,
                                                   @RequestBody ClasePutDTO clasePutDTO) {
        ClaseGetDTO claseActualizada = claseService.updateClase(id, clasePutDTO);
        return ResponseEntity.ok(claseActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        claseService.deleteClase(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleClaseStatus(@PathVariable Long id) {
        claseService.toggleClaseStatus(id);
        return ResponseEntity.noContent().build();
    }


}