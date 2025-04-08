package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.RoomGetDTO;
import com.gymmanagement.backend.dto.post.RoomPostDTO;
import com.gymmanagement.backend.dto.put.RoomPutDTO;

import java.util.List;

public interface RoomService {
    List<RoomGetDTO> findAllRooms();

    RoomGetDTO findRoomById(Long id);

    RoomGetDTO saveRoom(RoomPostDTO roomPostDTO);

    RoomGetDTO updateRoom(Long id, RoomPutDTO roomPutDTO);

    void deleteRoom(Long id);
}
