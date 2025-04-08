package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.StaffMemberGetDTO;
import com.gymmanagement.backend.dto.post.StaffMemberPostDTO;
import com.gymmanagement.backend.dto.put.StaffMemberPutDTO;
import com.gymmanagement.backend.service.interfaces.StaffMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/staff-members")
public class StaffMemberController {

    @Autowired
    private StaffMemberService staffMemberService;

    @GetMapping
    public ResponseEntity<List<StaffMemberGetDTO>> getAllStaffMembers() {
        return ResponseEntity.ok(staffMemberService.findAllStaffMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffMemberGetDTO> getStaffMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(staffMemberService.findStaffMemberById(id));
    }

    @PostMapping
    public ResponseEntity<StaffMemberGetDTO> createStaffMember(@RequestBody StaffMemberPostDTO staffMemberPostDTO) {
        return ResponseEntity.ok(staffMemberService.saveStaffMember(staffMemberPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffMemberGetDTO> updateStaffMember(@PathVariable Long id, @RequestBody StaffMemberPutDTO staffMemberPutDTO) {
        return ResponseEntity.ok(staffMemberService.updateStaffMember(id, staffMemberPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaffMember(@PathVariable Long id) {
        staffMemberService.deleteStaffMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{staffType}")
    public ResponseEntity<List<StaffMemberGetDTO>> getStaffMembersByType(@PathVariable String staffType) {
        return ResponseEntity.ok(staffMemberService.findByType(staffType));
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long staffMemberId,
            @RequestParam DayOfWeek day,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {
        return ResponseEntity.ok(staffMemberService.isAvailable(staffMemberId, day, startTime, endTime));
    }
}
