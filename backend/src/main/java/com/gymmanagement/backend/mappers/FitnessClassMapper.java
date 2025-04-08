package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.FitnessClassGetDTO;
import com.gymmanagement.backend.dto.post.FitnessClassPostDTO;
import com.gymmanagement.backend.dto.put.FitnessClassPutDTO;
import com.gymmanagement.backend.model.FitnessClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FitnessClassMapper {

    @Mapping(target = "id", ignore = true)
    @Named("mapPostDtoToFitnessClassEntity")
    FitnessClass mapPostDtoToFitnessClassEntity(FitnessClassPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Named("updateFitnessClassEntityFromPutDto")
    void updateFitnessClassEntityFromPutDto(FitnessClassPutDTO dto, @MappingTarget FitnessClass entity);

    @Named("mapFitnessClassEntityToGetDto")
    FitnessClassGetDTO mapFitnessClassEntityToGetDto(FitnessClass entity);
}
