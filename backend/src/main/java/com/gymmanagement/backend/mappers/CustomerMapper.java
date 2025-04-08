package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.CustomerGetDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.gymmanagement.backend.dto.post.ClientePostDTO;
import com.gymmanagement.backend.dto.put.ClientePutDTO;
import com.gestiongimnasio.backend.model.Cliente;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", source = "dto.usuarioPostDTO.nombre")
    @Mapping(target = "contrasena", source = "dto.usuarioPostDTO.contrasena")
    @Mapping(target = "correo", source = "dto.usuarioPostDTO.correo")
    @Mapping(target = "telefono", source = "dto.usuarioPostDTO.telefono")
    @Mapping(target = "fechaNacimiento", source = "dto.usuarioPostDTO.fechaNacimiento")
    @Mapping(target = "tipoUsuario", source = "dto.usuarioPostDTO.tipoUsuario")
    @Mapping(target = "activo", source = "dto.usuarioPostDTO.activo")
    @Mapping(target = "fechaRegistro", ignore=true)

    @Named("fromClientePostDTO")
    Cliente fromClientePostDTO(ClientePostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaNacimiento", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(source = "usuarioPutDTO.nombre", target = "nombre")
    @Mapping(source = "usuarioPutDTO.correo", target = "correo")
    @Mapping(source = "usuarioPutDTO.telefono", target = "telefono")
    @Mapping(source = "usuarioPutDTO.activo", target = "activo")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(target = "contrasena", source = "dto.usuarioPutDTO.contrasena")
    @Named("fromClientePutDTO")
    void fromClientePutDTO(ClientePutDTO dto, @MappingTarget Cliente entity);

    @Mapping(target = "usuario", source = ".", qualifiedByName = "toUsuarioGetDTO")

    @Named("toClienteGetDTO")
    CustomerGetDTO toClienteGetDTO(Cliente entity);

}