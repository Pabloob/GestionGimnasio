package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.CustomerGetDTO;
import com.gymmanagement.backend.dto.post.CustomerPostDTO;
import com.gymmanagement.backend.dto.put.CustomerPutDTO;
import com.gymmanagement.backend.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.user.name")
    @Mapping(target = "password", source = "dto.user.password")
    @Mapping(target = "email", source = "dto.user.email")
    @Mapping(target = "phone", source = "dto.user.phone")
    @Mapping(target = "birthDate", source = "dto.user.birthDate")
    @Mapping(target = "userType", source = "dto.user.userType")
    @Mapping(target = "active", source = "dto.user.active")
    @Mapping(target = "registrationDate", ignore = true)
    @Named("mapPostDtoToCustomerEntity")
    Customer mapPostDtoToCustomerEntity(CustomerPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "userType", ignore = true)
    @Mapping(source = "userPutDTO.name", target = "name")
    @Mapping(source = "userPutDTO.email", target = "email")
    @Mapping(source = "userPutDTO.phone", target = "phone")
    @Mapping(source = "userPutDTO.active", target = "active")
    @Mapping(source = "userPutDTO.password", target = "password")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Named("updateCustomerEntityFromPutDto")
    void updateCustomerEntityFromPutDto(CustomerPutDTO dto, @MappingTarget Customer entity);

    @Mapping(target = "user", source = ".", qualifiedByName = "mapUserEntityToGetDto")
    @Named("mapCustomerEntityToGetDto")
    CustomerGetDTO mapCustomerEntityToGetDto(Customer entity);
}

