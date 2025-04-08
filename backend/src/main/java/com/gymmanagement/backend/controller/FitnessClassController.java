package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.FitnessClassGetDTO;
import com.gymmanagement.backend.dto.post.FitnessClassPostDTO;
import com.gymmanagement.backend.dto.put.FitnessClassPutDTO;
import com.gymmanagement.backend.service.interfaces.FitnessClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class FitnessClassController {

    private final FitnessClassService fitnessClassService;

    public FitnessClassController(FitnessClassService fitnessClassService) {
        this.fitnessClassService = fitnessClassService;
    }

    @GetMapping
    public ResponseEntity<List<FitnessClassGetDTO>> getAllClasses() {
        List<FitnessClassGetDTO> classes = fitnessClassService.findAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/active")
    public ResponseEntity<List<FitnessClassGetDTO>> getActiveClasses() {
        List<FitnessClassGetDTO> activeClasses = fitnessClassService.findActiveClasses();
        return ResponseEntity.ok(activeClasses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessClassGetDTO> getClassById(@PathVariable Long id) {
        FitnessClassGetDTO fitnessClass = fitnessClassService.findClassById(id);
        return ResponseEntity.ok(fitnessClass);
    }

    @GetMapping("/{id}/available-spots")
    public ResponseEntity<Integer> getAvailableSpots(@PathVariable Long id) {
        int slots = fitnessClassService.availableSpots(id);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{id}/has-available-spots")
    public ResponseEntity<Boolean> hasAvailableSpots(@PathVariable Long id) {
        boolean hasSlots = fitnessClassService.hasAvailableSpots(id);
        return ResponseEntity.ok(hasSlots);
    }

    @PostMapping
    public ResponseEntity<FitnessClassGetDTO> createClass(@RequestBody FitnessClassPostDTO dto) {
        FitnessClassGetDTO createdClass = fitnessClassService.saveClass(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessClassGetDTO> updateClass(@PathVariable Long id,
                                                          @RequestBody FitnessClassPutDTO dto) {
        FitnessClassGetDTO updatedClass = fitnessClassService.updateClass(id, dto);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        fitnessClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleClassStatus(@PathVariable Long id) {
        fitnessClassService.toggleClassStatus(id);
        return ResponseEntity.noContent().build();
    }
}
