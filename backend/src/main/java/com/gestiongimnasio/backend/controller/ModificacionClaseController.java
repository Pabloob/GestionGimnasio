package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.ModificacionClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ModificacionClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ModificacionClasePutDTO;
import com.gestiongimnasio.backend.service.ModificacionClaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modificaciones-clase")
public class ModificacionClaseController {

    private final ModificacionClaseService modificacionClaseService;

    public ModificacionClaseController(ModificacionClaseService modificacionClaseService) {
        this.modificacionClaseService = modificacionClaseService;
    }

    @GetMapping
    public ResponseEntity<List<ModificacionClaseGetDTO>> getAll() {
        List<ModificacionClaseGetDTO> modificaciones = modificacionClaseService.getAll();
        return ResponseEntity.ok(modificaciones);
    }

    @GetMapping("/clase/{id}")
    public ResponseEntity<ModificacionClaseGetDTO> getByClase(@PathVariable Long id) {
        ModificacionClaseGetDTO modificaciones = modificacionClaseService.getByClase(id);
        return ResponseEntity.ok(modificaciones);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<ModificacionClaseGetDTO> getByInstructor(@PathVariable Long id) {
        ModificacionClaseGetDTO modificaciones = modificacionClaseService.getByInstructor(id);
        return ResponseEntity.ok(modificaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModificacionClaseGetDTO> getById(@PathVariable Long id) {
        ModificacionClaseGetDTO modificacion = modificacionClaseService.getById(id);
        return ResponseEntity.ok(modificacion);
    }

    @PostMapping
    public ResponseEntity<ModificacionClaseGetDTO> save(@RequestBody ModificacionClasePostDTO modificacionClaseDTO) {
        ModificacionClaseGetDTO nuevaModificacion = modificacionClaseService.save(modificacionClaseDTO);
        return new ResponseEntity<>(nuevaModificacion, HttpStatus.CREATED);
    }

    @PutMapping({"/id"})
    public ResponseEntity<ModificacionClaseGetDTO> update(@RequestBody Long id, @RequestBody ModificacionClasePutDTO modificacionClaseDTO) {
        ModificacionClaseGetDTO nuevaModificacion = modificacionClaseService.update(id, modificacionClaseDTO);
        return new ResponseEntity<>(nuevaModificacion, HttpStatus.CREATED);
    }

}