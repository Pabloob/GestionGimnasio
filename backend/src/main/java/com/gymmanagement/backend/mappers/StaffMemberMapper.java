package com.gymmanagement.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.gymmanagement.backend.dto.get.StaffMemberGetDTO;
import com.gymmanagement.backend.dto.post.StaffMemberPostDTO;
import com.gymmanagement.backend.dto.put.StaffMemberPutDTO;
import com.gymmanagement.backend.model.StaffMember;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StaffMemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.user.name")
    @Mapping(target = "password", source = "dto.user.password")
    @Mapping(target = "email", source = "dto.user.email")
    @Mapping(target = "phone", source = "dto.user.phone")
    @Mapping(target = "birthDate", source = "dto.user.birthDate")
    @Mapping(target = "userType", source = "dto.user.userType")
    @Mapping(target = "active", source = "dto.user.active")
    @Mapping(target = "registrationDate", ignore = true)
    @Named("mapPostDtoToStaffEntity")
    StaffMember mapPostDtoToStaffEntity(StaffMemberPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.userPutDTO.name")
    @Mapping(target = "password", source = "dto.userPutDTO.password")
    @Mapping(target = "email", source = "dto.userPutDTO.email")
    @Mapping(target = "phone", source = "dto.userPutDTO.phone")
    @Mapping(target = "active", source = "dto.userPutDTO.active")
    @Mapping(target = "userType", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Named("updateStaffEntityFromPutDto")
    void updateStaffEntityFromPutDto(StaffMemberPutDTO dto, @MappingTarget StaffMember entity);

    @Mapping(target = "user", source = ".", qualifiedByName = "mapUserEntityToGetDto")
    @Named("mapStaffEntityToGetDto")
    StaffMemberGetDTO mapStaffEntityToGetDto(StaffMember entity);
}
