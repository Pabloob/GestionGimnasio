package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.ModificacionClaseDTO;
import com.gestiongimnasio.backend.service.ModificacionClaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<List<ModificacionClaseDTO>> getAllModificaciones() {
        List<ModificacionClaseDTO> modificaciones = modificacionClaseService.getAllModificaciones();
        return ResponseEntity.ok(modificaciones);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<ModificacionClaseDTO> getModificacionById(@PathVariable Integer id) {
        ModificacionClaseDTO modificacion = modificacionClaseService.getModificacionById(id);
        return ResponseEntity.ok(modificacion);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<ModificacionClaseDTO> createModificacion(@RequestBody ModificacionClaseDTO modificacionClaseDTO) {
        ModificacionClaseDTO nuevaModificacion = modificacionClaseService.saveModificacion(modificacionClaseDTO);
        return new ResponseEntity<>(nuevaModificacion, HttpStatus.CREATED);
    }

    @GetMapping("/clase/{claseId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<List<ModificacionClaseDTO>> getModificacionesByClase(@PathVariable Integer claseId) {
        List<ModificacionClaseDTO> modificaciones = modificacionClaseService.getModificacionesByClase(claseId);
        return ResponseEntity.ok(modificaciones);
    }

    @GetMapping("/instructor/{instructorId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public ResponseEntity<List<ModificacionClaseDTO>> getModificacionesByInstructor(@PathVariable Long instructorId) {
        List<ModificacionClaseDTO> modificaciones = modificacionClaseService.getModificacionesByInstructor(instructorId);
        return ResponseEntity.ok(modificaciones);
    }
}