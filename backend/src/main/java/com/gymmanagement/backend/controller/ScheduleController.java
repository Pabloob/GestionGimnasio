package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.ScheduleGetDTO;
import com.gymmanagement.backend.dto.post.SchedulePostDTO;
import com.gymmanagement.backend.dto.put.SchedulePutDTO;
import com.gymmanagement.backend.service.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleGetDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleGetDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ScheduleGetDTO>> getSchedulesByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(scheduleService.findByClass(classId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ScheduleGetDTO>> getSchedulesByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(scheduleService.findByRoom(roomId));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ScheduleGetDTO>> getSchedulesByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(scheduleService.findByInstructor(instructorId));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<ScheduleGetDTO>> getSchedulesByDay(@PathVariable DayOfWeek day) {
        return ResponseEntity.ok(scheduleService.findByDay(day));
    }

    @PostMapping
    public ResponseEntity<ScheduleGetDTO> createSchedule(@RequestBody SchedulePostDTO schedulePostDTO) {
        return ResponseEntity.ok(scheduleService.create(schedulePostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleGetDTO> updateSchedule(@PathVariable Long id, @RequestBody SchedulePutDTO schedulePutDTO) {
        return ResponseEntity.ok(scheduleService.update(id, schedulePutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
