package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.SalaGetDTO;
import com.gestiongimnasio.backend.dto.post.SalaPostDTO;
import com.gestiongimnasio.backend.dto.put.SalaPutDTO;
import com.gestiongimnasio.backend.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaGetDTO>> getAllSalas() {
        return ResponseEntity.ok(salaService.findAllSalas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaGetDTO> getSalaById(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.findSalaById(id));
    }

    @PostMapping
    public ResponseEntity<SalaGetDTO> createSala(@RequestBody SalaPostDTO salaPostDTO) {
        return ResponseEntity.ok(salaService.saveSala(salaPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaGetDTO> updateSala(@PathVariable Long id, @RequestBody SalaPutDTO salaPutDTO) {
        return ResponseEntity.ok(salaService.updateSala(id, salaPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        salaService.deleteSala(id);
        return ResponseEntity.noContent().build();
    }

}