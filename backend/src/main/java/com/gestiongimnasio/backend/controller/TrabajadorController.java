package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<List<TrabajadorGetDTO>> getAllTrabajadores() {
        return ResponseEntity.ok(trabajadorService.findAllTrabajadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorGetDTO> getTrabajadorById(@PathVariable Long id) {
        return ResponseEntity.ok(trabajadorService.findTrabajadorById(id));
    }

    @PostMapping
    public ResponseEntity<TrabajadorGetDTO> createTrabajador(@RequestBody TrabajadorPostDTO trabajadorPostDTO) {
        return ResponseEntity.ok(trabajadorService.saveTrabajador(trabajadorPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorGetDTO> updateTrabajador(@PathVariable Long id, @RequestBody TrabajadorPutDTO trabajadorPutDTO) {
        return ResponseEntity.ok(trabajadorService.updateTrabajador(id, trabajadorPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        trabajadorService.deleteTrabajador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipoTrabajador}")
    public ResponseEntity<List<TrabajadorGetDTO>> getTrabajadoresByTipo(@PathVariable String tipoTrabajador) {
        return ResponseEntity.ok(trabajadorService.findByTipo(tipoTrabajador));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> checkDisponibilidad(
            @RequestParam Long trabajadorId,
            @RequestParam DayOfWeek dia,
            @RequestParam LocalTime horaInicio,
            @RequestParam LocalTime horaFin) {
        return ResponseEntity.ok(trabajadorService.estaDisponible(trabajadorId, dia, horaInicio, horaFin));
    }
}