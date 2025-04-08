package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.RoomGetDTO;
import com.gymmanagement.backend.dto.post.RoomPostDTO;
import com.gymmanagement.backend.dto.put.RoomPutDTO;
import com.gymmanagement.backend.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Named("mapPostDtoToRoomEntity")
    Room mapPostDtoToRoomEntity(RoomPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Named("updateRoomEntityFromPutDto")
    void updateRoomEntityFromPutDto(RoomPutDTO dto, @MappingTarget Room entity);

    @Named("mapRoomEntityToGetDto")
    RoomGetDTO mapRoomEntityToGetDto(Room entity);
}
