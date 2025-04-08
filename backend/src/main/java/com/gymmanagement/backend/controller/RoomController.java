package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.RoomGetDTO;
import com.gymmanagement.backend.dto.post.RoomPostDTO;
import com.gymmanagement.backend.dto.put.RoomPutDTO;
import com.gymmanagement.backend.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomGetDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomGetDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomGetDTO> createRoom(@RequestBody RoomPostDTO roomPostDTO) {
        return ResponseEntity.ok(roomService.saveRoom(roomPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomGetDTO> updateRoom(@PathVariable Long id, @RequestBody RoomPutDTO roomPutDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

}
