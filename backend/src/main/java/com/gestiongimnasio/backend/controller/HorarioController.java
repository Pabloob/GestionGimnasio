package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.ClaseHorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.ClaseHorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.ClaseHorarioPutDTO;
import com.gestiongimnasio.backend.service.ClaseHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ClaseHorarioController {

    @Autowired
    private ClaseHorarioService reservaService;

    @GetMapping
    public ResponseEntity<List<ClaseHorarioGetDTO>> getAllReservas() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseHorarioGetDTO> getReservaById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClaseHorarioGetDTO> createReserva(@RequestBody ClaseHorarioPostDTO claseHorarioPostDTO) {
        return ResponseEntity.ok(reservaService.create(claseHorarioPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseHorarioGetDTO> updateReserva(@PathVariable Long id, @RequestBody ClaseHorarioPutDTO claseHorarioPutDTO) {
        return ResponseEntity.ok(reservaService.update(id, claseHorarioPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<ClaseHorarioGetDTO>> getReservasByClase(@PathVariable Long claseId) {
        return ResponseEntity.ok(reservaService.findByClase(claseId));
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<ClaseHorarioGetDTO>> getReservasBySala(@PathVariable Long salaId) {
        return ResponseEntity.ok(reservaService.findBySala(salaId));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ClaseHorarioGetDTO>> getReservasByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(reservaService.findByInstructor(instructorId));
    }

    @GetMapping("/dia/{dia}")
    public ResponseEntity<List<ClaseHorarioGetDTO>> getReservasByDia(@PathVariable DayOfWeek dia) {
        return ResponseEntity.ok(reservaService.findByDia(dia));
    }
}