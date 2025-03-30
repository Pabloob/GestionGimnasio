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
    public ResponseEntity<List<ClaseGetDTO>> getAll() {
        List<ClaseGetDTO> clases = claseService.getAll();
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseGetDTO> getById(@PathVariable Long id) {
        ClaseGetDTO clase = claseService.getById(id);
        return ResponseEntity.ok(clase);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<ClaseGetDTO>> getClasesByInstructor(@PathVariable Long id) {
        List<ClaseGetDTO> clases = claseService.getClasesByInstructor(id);
        return ResponseEntity.ok(clases);
    }

    @PostMapping
    public ResponseEntity<ClaseGetDTO> save(@RequestBody ClasePostDTO claseDTO) {
        ClaseGetDTO nuevaClase = claseService.save(claseDTO);
        return new ResponseEntity<>(nuevaClase, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseGetDTO> update(@PathVariable Long id, @RequestBody ClasePutDTO claseDTO) {
        ClaseGetDTO updatedClase = claseService.update(id, claseDTO);
        return ResponseEntity.ok(updatedClase);
    }

    @PutMapping("/confirmar/{id}")
    public ResponseEntity<Void> confirmarAsistencias(@PathVariable Long id) {
        claseService.confirmarAsistencias(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        claseService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}