package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.UsuarioGetDTO;
import com.gestiongimnasio.backend.dto.put.UsuarioPutDTO;
import com.gestiongimnasio.backend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Actualización de Entidad desde PUT DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)

    @Named("fromUsuarioPutDTO")
    void fromUsuarioPutDTO(UsuarioPutDTO dto, @MappingTarget Usuario entity);

    @Named("toUsuarioGetDTO")
    UsuarioGetDTO toUsuarioGetDTO(Usuario entity);
}