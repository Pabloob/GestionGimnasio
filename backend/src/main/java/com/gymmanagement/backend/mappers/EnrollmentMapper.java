package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.EnrollmentGetDTO;
import com.gymmanagement.backend.dto.post.EnrollmentPostDTO;
import com.gymmanagement.backend.dto.put.EnrollmentPutDTO;
import com.gymmanagement.backend.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, FitnessClassMapper.class})
public interface EnrollmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "fitnessClass", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Named("mapPostDtoToEnrollmentEntity")
    Enrollment mapPostDtoToEnrollmentEntity(EnrollmentPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "fitnessClass", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Named("updateEnrollmentEntityFromPutDto")
    void updateEnrollmentEntityFromPutDto(EnrollmentPutDTO dto, @MappingTarget Enrollment entity);

    @Mapping(target = "customer", source = "customer", qualifiedByName = "mapCustomerEntityToGetDto")
    @Mapping(target = "fitnessClass", source = "fitnessClass", qualifiedByName = "mapFitnessClassEntityToGetDto")
    @Named("mapEnrollmentEntityToGetDto")
    EnrollmentGetDTO mapEnrollmentEntityToGetDto(Enrollment entity);
}
