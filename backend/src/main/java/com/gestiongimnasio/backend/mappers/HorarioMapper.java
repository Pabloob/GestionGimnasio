package com.gestiongimnasio.backend.mappers;

import com.gestiongimnasio.backend.dto.get.HorarioGetDTO;
import com.gestiongimnasio.backend.dto.post.HorarioPostDTO;
import com.gestiongimnasio.backend.dto.put.HorarioPutDTO;
import com.gestiongimnasio.backend.model.Horario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ClaseMapper.class, SalaMapper.class, TrabajadorMapper.class})
public interface HorarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "fechasInicio", source = "fechasInicio")
    @Mapping(target = "fechaFin", source = "fechaFin")

    @Named("fromHorarioPostDTO")
    Horario fromHorarioPostDTO(HorarioPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clase", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "fechasInicio", source = "fechasInicio")
    @Mapping(target = "fechaFin", source = "fechaFin")

    @Named("fromHorarioPutDTO")
    void fromHorarioPutDTO(HorarioPutDTO dto, @MappingTarget Horario entity);

    @Mapping(target = "clase", source = "clase", qualifiedByName = "toClaseGetDto")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "toSalaGetDTO")
    @Mapping(target = "instructor", source = "instructor", qualifiedByName = "toTrabajadorGetDTO")

    @Named("toHorarioGetDTO")
    HorarioGetDTO toHorarioGetDTO(Horario entity);

}