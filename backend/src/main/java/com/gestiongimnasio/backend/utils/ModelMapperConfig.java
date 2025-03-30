package com.gestiongimnasio.backend.utils;

import com.gestiongimnasio.backend.dto.get.*;
import com.gestiongimnasio.backend.dto.post.*;
import com.gestiongimnasio.backend.model.Usuario;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import com.gestiongimnasio.backend.model.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración básica
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);

        // Configurar todos los mapeos
        configureClaseMappings(modelMapper);
        configureUsuarioMappings(modelMapper);
        configureClienteMappings(modelMapper);
        configureTrabajadorMappings(modelMapper);
        configureInscripcionMappings(modelMapper);
        configurePagoMappings(modelMapper);
        configureModificacionClaseMappings(modelMapper);

        return modelMapper;
    }

    private void configureClaseMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(ClaseRequestDTO.class, Clase.class)
                .addMappings(mapper -> {
                    mapper.skip(Clase::setId);
                    mapper.skip(Clase::setInscripciones);
                    mapper.skip(Clase::setActiva);
                    mapper.<Integer>map(ClaseRequestDTO::getInstructorId,
                            (dest, v) -> dest.getInstructor().setId(v.longValue()));
                });

        // Entidad -> ResponseDTO
        modelMapper.createTypeMap(Clase.class, ClaseResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getInstructor().getId(), ClaseResponseDTO::setInstructorId);
                    mapper.map(src -> src.getInstructor().getNombre(), ClaseResponseDTO::setInstructorNombre);
                    mapper.map(src -> src.getInscripciones().size(), ClaseResponseDTO::setInscritosCount);
                });
    }

    private void configureUsuarioMappings(ModelMapper modelMapper) {
        // RegistroDTO -> Entidad
        modelMapper.createTypeMap(UsuarioRequestDTO.class, Usuario.class)
                .addMappings(mapper -> {
                    mapper.skip(Usuario::setId);
                    mapper.skip(Usuario::setFechaRegistro);
                    mapper.skip(Usuario::setActivo);
                });

        // Entidad -> ResponseDTO - Mapeo básico sin exclusiones
        modelMapper.createTypeMap(Usuario.class, UsuarioResponseDTO.class);

        // UpdateDTO -> Entidad
        modelMapper.createTypeMap(UsuarioRequestDTO.class, Usuario.class)
                .addMappings(mapper -> {
                    mapper.skip(Usuario::setId);
                    mapper.skip(Usuario::setFechaRegistro);
                    mapper.skip(Usuario::setTipoUsuario);
                    // Excluir campos que no se deben actualizar
                });
    }

    private void configureClienteMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(ClienteRequestDTO.class, Cliente.class)
                .addMappings(mapper -> {
                    mapper.skip(Cliente::setId);
                    mapper.skip(Cliente::setInscripciones);
                    mapper.skip(Cliente::setPagos);
                });

    }

    private void configureTrabajadorMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(TrabajadorRequestDTO.class, Trabajador.class)
                .addMappings(mapper -> {
                    mapper.skip(Trabajador::setId);
                    mapper.skip(Trabajador::setClases);
                });

    }

    private void configureInscripcionMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(InscripcionRequestDTO.class, Inscripcion.class)
                .addMappings(mapper -> {
                    mapper.skip(Inscripcion::setId);
                    mapper.skip(Inscripcion::setFechaRegistro);
                    mapper.skip(Inscripcion::setEstadoPago);
                    mapper.<Integer>map(InscripcionRequestDTO::getClienteId,
                            (dest, v) -> dest.getCliente().setId(v.longValue()));
                    mapper.<Integer>map(InscripcionRequestDTO::getClaseId,
                            (dest, v) -> dest.getClase().setId(v));
                });

        // Entidad -> ResponseDTO
        modelMapper.createTypeMap(Inscripcion.class, InscripcionResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCliente().getId(), InscripcionResponseDTO::setClienteId);
                    mapper.map(src -> src.getCliente().getNombre(), InscripcionResponseDTO::setClienteNombre);
                    mapper.map(src -> src.getClase().getId(), InscripcionResponseDTO::setClaseId);
                    mapper.map(src -> src.getClase().getNombre(), InscripcionResponseDTO::setClaseNombre);
                    mapper.map(src -> src.getPago() != null ? src.getPago().getId() : null,
                            InscripcionResponseDTO::setPagoId);
                });
    }

    private void configurePagoMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(PagoRequestDTO.class, Pago.class)
                .addMappings(mapper -> {
                    mapper.skip(Pago::setId);
                    mapper.skip(Pago::setInscripciones);
                    mapper.skip(Pago::setFechaPago);
                    mapper.<Long>map(PagoRequestDTO::getClienteId,
                            (dest, v) -> dest.getCliente().setId(v));
                });

        // Entidad -> ResponseDTO
        modelMapper.createTypeMap(Pago.class, PagoResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCliente().getId(), PagoResponseDTO::setClienteId);
                    mapper.map(src -> src.getCliente().getNombre(), PagoResponseDTO::setClienteNombre);
                    mapper.map(src -> src.getInscripciones().stream()
                            .map(Inscripcion::getId).toList(), PagoResponseDTO::setInscripcionesIds);
                });
    }

    private void configureModificacionClaseMappings(ModelMapper modelMapper) {
        // RequestDTO -> Entidad
        modelMapper.createTypeMap(ModificacionClaseRequestDTO.class, ModificacionClase.class)
                .addMappings(mapper -> {
                    mapper.skip(ModificacionClase::setId);
                    mapper.skip(ModificacionClase::setFechaModificacion);
                    mapper.<Integer>map(ModificacionClaseRequestDTO::getClaseId,
                            (dest, v) -> dest.getClase().setId(v));
                    mapper.<Long>map(ModificacionClaseRequestDTO::getInstructorId,
                            (dest, v) -> dest.getInstructor().setId(v));
                });

        // Entidad -> ResponseDTO
        modelMapper.createTypeMap(ModificacionClase.class, ModificacionClaseResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getClase().getId(), ModificacionClaseResponseDTO::setClaseId);
                    mapper.map(src -> src.getClase().getNombre(), ModificacionClaseResponseDTO::setClaseNombre);
                    mapper.map(src -> src.getInstructor().getId(), ModificacionClaseResponseDTO::setInstructorId);
                    mapper.map(src -> src.getInstructor().getNombre(), ModificacionClaseResponseDTO::setInstructorNombre);
                });
    }
}
