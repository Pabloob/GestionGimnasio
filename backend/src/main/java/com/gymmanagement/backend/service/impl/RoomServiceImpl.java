package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.RoomGetDTO;
import com.gymmanagement.backend.dto.post.RoomPostDTO;
import com.gymmanagement.backend.dto.put.RoomPutDTO;
import com.gymmanagement.backend.mappers.RoomMapper;
import com.gymmanagement.backend.model.Room;
import com.gymmanagement.backend.repository.ScheduleRepository;
import com.gymmanagement.backend.repository.RoomRepository;
import com.gymmanagement.backend.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ScheduleRepository scheduleRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomGetDTO> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::mapRoomEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomGetDTO findRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));
        return roomMapper.mapRoomEntityToGetDto(room);
    }

    @Override
    public RoomGetDTO saveRoom(RoomPostDTO dto) {
        if (roomRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("A room with that name already exists.");
        }

        Room room = roomMapper.mapPostDtoToRoomEntity(dto);
        Room saved = roomRepository.save(room);
        return roomMapper.mapRoomEntityToGetDto(saved);
    }

    @Override
    public RoomGetDTO updateRoom(Long id, RoomPutDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));

        if (dto.getName() != null &&
                !dto.getName().equals(room.getName()) &&
                roomRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("A room with that name already exists.");
        }

        roomMapper.updateRoomEntityFromPutDto(dto, room);
        Room updated = roomRepository.save(room);
        return roomMapper.mapRoomEntityToGetDto(updated);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with ID: " + id);
        }

        if (scheduleRepository.existsByRoomId(id)) {
            throw new RuntimeException("Cannot delete a room with associated bookings.");
        }

        roomRepository.deleteById(id);
    }
}
