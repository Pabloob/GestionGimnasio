package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.SalaGetDTO;
import com.gestiongimnasio.backend.dto.post.SalaPostDTO;
import com.gestiongimnasio.backend.dto.put.SalaPutDTO;
import com.gestiongimnasio.backend.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    @Mapping(target = "id", ignore = true)
    @Named("fromSalaPostDTO")
    Sala fromSalaPostDTO(SalaPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Named("fromSalaPutDTO")
    void fromSalaPutDTO(SalaPutDTO dto, @MappingTarget Sala entity);

    @Named("toSalaGetDTO")
    SalaGetDTO toSalaGetDTO(Sala entity);

}