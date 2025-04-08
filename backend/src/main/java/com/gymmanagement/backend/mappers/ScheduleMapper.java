package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.ScheduleGetDTO;
import com.gymmanagement.backend.dto.post.SchedulePostDTO;
import com.gymmanagement.backend.dto.put.SchedulePutDTO;
import com.gymmanagement.backend.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {FitnessClassMapper.class, RoomMapper.class, StaffMemberMapper.class})
public interface ScheduleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fitnessClass", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "startDates", source = "startDates")
    @Mapping(target = "endDate", source = "endDate")
    @Named("mapPostDtoToScheduleEntity")
    Schedule mapPostDtoToScheduleEntity(SchedulePostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fitnessClass", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "startDates", source = "startDates")
    @Mapping(target = "endDate", source = "endDate")
    @Named("updateScheduleEntityFromPutDto")
    void updateScheduleEntityFromPutDto(SchedulePutDTO dto, @MappingTarget Schedule entity);

    @Mapping(target = "fitnessClass", source = "fitnessClass", qualifiedByName = "mapFitnessClassEntityToGetDto")
    @Mapping(target = "room", source = "room", qualifiedByName = "mapRoomEntityToGetDto")
    @Mapping(target = "instructor", source = "instructor", qualifiedByName = "mapStaffMemberEntityToGetDto")
    @Named("mapScheduleEntityToGetDto")
    ScheduleGetDTO mapScheduleEntityToGetDto(Schedule entity);
}
