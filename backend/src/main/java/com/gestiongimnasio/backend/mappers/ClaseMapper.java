package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.post.ClasePostDTO;
import com.gestiongimnasio.backend.dto.put.ClasePutDTO;
import com.gestiongimnasio.backend.model.Clase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClaseMapper {

    @Named("fromClasePostDTO")
    Clase fromClasePostDTO(ClasePostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Named("fromClasePutDTO")
    void fromClasePutDTO(ClasePutDTO dto, @MappingTarget Clase entity);

    @Named("toClaseGetDto")
    ClaseGetDTO toClaseGetDto(Clase entity);

}