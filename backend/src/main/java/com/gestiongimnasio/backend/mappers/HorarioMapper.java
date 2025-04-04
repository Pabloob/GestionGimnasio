package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.HorarioGetDTO;
import com.gestiongimnasio.backend.dto.get.SalaGetDTO;
import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.get.simpleClases.ClaseSimpleDTO;
import com.gestiongimnasio.backend.dto.post.HorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.HorarioPutDTO;
import com.gestiongimnasio.backend.model.Horario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ClaseMapper.class, SalaMapper.class, TrabajadorMapper.class})
public interface ClaseHorarioMapper {

    ClaseHorarioMapper INSTANCE = Mappers.getMapper(ClaseHorarioMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    Horario toEntity(HorarioPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    void updateFromDto(HorarioPutDTO dto, @MappingTarget Horario entity);

    @Mapping(target = "clase", source = "clase", qualifiedByName = "toClaseGetDTO")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "toSalaGetDTO")
    @Mapping(target = "instructor", source = "instructor", qualifiedByName = "toTrabajadorGetDTO")
    HorarioGetDTO toGetDto(Horario entity);

    @Named("toClaseGetDTO")
    default ClaseSimpleDTO toClaseGetDTO(com.gestiongimnasio.backend.model.Clase clase) {
        if (clase == null) {
            return null;
        }
        ClaseSimpleDTO dto = new ClaseSimpleDTO();
        dto.setId(clase.getId());
        dto.setNombre(clase.getNombre());
        dto.setCapacidadMaxima(clase.getCapacidadMaxima());
        dto.setPrecio(clase.getPrecio());
        dto.setActiva(clase.isActiva());
        return dto;
    }

    @Named("toSalaGetDTO")
    default SalaGetDTO toSalaGetDTO(com.gestiongimnasio.backend.model.Sala sala) {
        if (sala == null) {
            return null;
        }
        return SalaMapper.INSTANCE.toGetDto(sala);
    }

    @Named("toTrabajadorGetDTO")
    default TrabajadorGetDTO toTrabajadorGetDTO(com.gestiongimnasio.backend.model.Trabajador instructor) {
        if (instructor == null) {
            return null;
        }
        return TrabajadorMapper.INSTANCE.toGetDto(instructor);
    }
}