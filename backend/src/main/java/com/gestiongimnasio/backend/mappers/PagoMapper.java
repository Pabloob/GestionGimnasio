package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.model.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface PagoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaPago", ignore = true)
    Pago toEntity(PagoPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "fechaPago", ignore = true)
    void updateFromDto(PagoPutDTO dto, @MappingTarget Pago entity);

    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "toClienteGetDTO")
    PagoGetDTO toGetDto(Pago entity);

}