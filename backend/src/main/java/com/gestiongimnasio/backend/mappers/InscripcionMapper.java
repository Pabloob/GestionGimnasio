package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;
import com.gestiongimnasio.backend.model.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ClaseMapper.class})
public interface InscripcionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Inscripcion toEntity(InscripcionPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    void updateFromDto(InscripcionPutDTO dto, @MappingTarget Inscripcion entity);

    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "toClienteGetDTO")
    @Mapping(target = "clase", source = "clase", qualifiedByName = "toClaseGetDto")
    InscripcionGetDTO toGetDto(Inscripcion entity);
}