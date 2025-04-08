package com.gymmanagement.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.gymmanagement.backend.dto.get.UserGetDTO;
import com.gymmanagement.backend.dto.put.UserPutDTO;
import com.gymmanagement.backend.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "userType", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Named("updateUserEntityFromPutDto")
    void updateUserEntityFromPutDto(UserPutDTO dto, @MappingTarget User entity);

    @Named("mapUserEntityToGetDto")
    UserGetDTO mapUserEntityToGetDto(User entity);
}
