package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.HorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.HorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.HorarioPutDTO;
import com.gestiongimnasio.backend.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioGetDTO>> getAllHorarios() {
        return ResponseEntity.ok(horarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioGetDTO> getHorarioById(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.findById(id));
    }

    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<HorarioGetDTO>> getHorariosByClase(@PathVariable Long claseId) {
        return ResponseEntity.ok(horarioService.findByClase(claseId));
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<HorarioGetDTO>> getHorariosBySala(@PathVariable Long salaId) {
        return ResponseEntity.ok(horarioService.findBySala(salaId));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<HorarioGetDTO>> getHorariosByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(horarioService.findByInstructor(instructorId));
    }

    @GetMapping("/dia/{dia}")
    public ResponseEntity<List<HorarioGetDTO>> getHorariosByDia(@PathVariable DayOfWeek dia) {
        return ResponseEntity.ok(horarioService.findByDia(dia));
    }

    @PostMapping
    public ResponseEntity<HorarioGetDTO> createHorario(@RequestBody HorarioPostDTO horarioPostDTO) {
        return ResponseEntity.ok(horarioService.create(horarioPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioGetDTO> updateHorario(@PathVariable Long id, @RequestBody HorarioPutDTO horarioPutDTO) {
        return ResponseEntity.ok(horarioService.update(id, horarioPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        horarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}