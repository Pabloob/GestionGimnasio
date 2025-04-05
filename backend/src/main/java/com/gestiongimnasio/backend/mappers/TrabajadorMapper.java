package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.model.Trabajador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface TrabajadorMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", source = "dto.usuarioPostDTO.nombre")
    @Mapping(target = "contrasena", source = "dto.usuarioPostDTO.contrasena")
    @Mapping(target = "correo", source = "dto.usuarioPostDTO.correo")
    @Mapping(target = "telefono", source = "dto.usuarioPostDTO.telefono")
    @Mapping(target = "fechaNacimiento", source = "dto.usuarioPostDTO.fechaNacimiento")
    @Mapping(target = "tipoUsuario", source = "dto.usuarioPostDTO.tipoUsuario")
    @Mapping(target = "activo", source = "dto.usuarioPostDTO.activo")

    @Named("fromTrabajadorPostDTO")
    Trabajador fromTrabajadorPostDTO(TrabajadorPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Named("fromTrabajadorPutDTO")
    void fromTrabajadorPutDTO(TrabajadorPutDTO dto, @MappingTarget Trabajador entity);

    @Mapping(target = "usuario", source = ".", qualifiedByName = "toUsuarioGetDTO")

    @Named("toTrabajadorGetDTO")
    TrabajadorGetDTO toTrabajadorGetDTO(Trabajador entity);

}