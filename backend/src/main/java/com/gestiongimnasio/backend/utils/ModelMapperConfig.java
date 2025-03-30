package com.gestiongimnasio.backend.utils;

import com.gestiongimnasio.backend.dto.get.*;
import com.gestiongimnasio.backend.dto.post.*;
import com.gestiongimnasio.backend.dto.put.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import com.gestiongimnasio.backend.model.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true);

        // Agregar configuraciones específicas
        configurarMapeosUsuario(modelMapper);
        configurarMapeosCliente(modelMapper);
        configurarMapeosTrabajador(modelMapper);
        configurarMapeosClase(modelMapper);
        configurarMapeosModificacionClase(modelMapper);
        configurarMapeosInscripcion(modelMapper);
        configurarMapeosPago(modelMapper);

        return modelMapper;
    }

    private void configurarMapeosUsuario(ModelMapper modelMapper) {
        // De Model a GetDTO
        modelMapper.typeMap(Usuario.class, UsuarioGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Usuario::getId, UsuarioGetDTO::setId);
                    mapper.map(Usuario::getNombre, UsuarioGetDTO::setNombre);
                    mapper.map(Usuario::getCorreo, UsuarioGetDTO::setCorreo);
                    mapper.map(Usuario::getTelefono, UsuarioGetDTO::setTelefono);
                    mapper.map(Usuario::getFechaNacimiento, UsuarioGetDTO::setFechaNacimiento);
                    mapper.map(Usuario::getFechaRegistro, UsuarioGetDTO::setFechaRegistro);
                    mapper.map(Usuario::getTipoUsuario, UsuarioGetDTO::setTipoUsuario);
                    mapper.map(Usuario::isActivo, UsuarioGetDTO::setActivo);
                });

        // De PostDTO a Model
        modelMapper.typeMap(UsuarioPostDTO.class, Usuario.class)
                .addMappings(mapper -> {
                    mapper.skip(Usuario::setId);
                    mapper.skip(Usuario::setFechaRegistro);
                    mapper.skip(Usuario::setActivo); // Se establece por defecto en true
                });

        // De PutDTO a Model
        modelMapper.typeMap(UsuarioPutDTO.class, Usuario.class)
                .addMappings(mapper -> {
                });
    }

    private void configurarMapeosCliente(ModelMapper modelMapper) {
        // De Model a GetDTO

        modelMapper.typeMap(Cliente.class, ClienteGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Cliente::getId, ClienteGetDTO::setId);
                    mapper.map(Cliente::getNombre, ClienteGetDTO::setNombre);
                    mapper.map(Cliente::getCorreo, ClienteGetDTO::setCorreo);
                    mapper.map(Cliente::getTelefono, ClienteGetDTO::setTelefono);
                    mapper.map(Cliente::getFechaNacimiento, ClienteGetDTO::setFechaNacimiento);
                    mapper.map(Cliente::getInasistencias, ClienteGetDTO::setInasistencias);
                    mapper.map(Cliente::isActivo, ClienteGetDTO::setActivo);
                    mapper.skip(ClienteGetDTO::setInscripcionesIds); // Se manejará manualmente
                });

        // De PostDTO a Model
        modelMapper.typeMap(ClientePostDTO.class, Cliente.class)
                .addMappings(mapper -> {
                    mapper.skip(Cliente::setId);
                    mapper.skip(Cliente::setInscripciones); // Relación ignorada
                    mapper.skip(Cliente::setPagos); // Relación ignorada
                    mapper.map(src -> Usuario.TipoUsuario.CLIENTE, Cliente::setTipoUsuario);
                    mapper.map(src -> true, Cliente::setActivo); // Por defecto activo
                });

        // De PutDTO a Model
        modelMapper.typeMap(ClientePutDTO.class, Cliente.class)
                .addMappings(mapper -> {
                    mapper.skip(Cliente::setInscripciones); // Relación ignorada
                    mapper.skip(Cliente::setPagos); // Relación ignorada
                    mapper.skip(Cliente::setTipoUsuario); // No se actualiza
                });
    }

    private void configurarMapeosTrabajador(ModelMapper modelMapper) {
        // De Model a GetDTO
        modelMapper.typeMap(Trabajador.class, TrabajadorGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Trabajador::getId, TrabajadorGetDTO::setId);
                    mapper.map(Trabajador::getNombre, TrabajadorGetDTO::setNombre);
                    mapper.map(Trabajador::getCorreo, TrabajadorGetDTO::setCorreo);
                    mapper.map(Trabajador::getTelefono, TrabajadorGetDTO::setTelefono);
                    mapper.map(Trabajador::getFechaNacimiento, TrabajadorGetDTO::setFechaNacimiento);
                    mapper.map(Trabajador::getDireccion, TrabajadorGetDTO::setDireccion);
                    mapper.map(Trabajador::getHoraInicio, TrabajadorGetDTO::setHoraInicio);
                    mapper.map(Trabajador::getHoraFin, TrabajadorGetDTO::setHoraFin);
                    mapper.map(Trabajador::getTipoTrabajador, TrabajadorGetDTO::setTipoTrabajador);
                    mapper.map(Trabajador::isActivo, TrabajadorGetDTO::setActivo);
                    mapper.skip(TrabajadorGetDTO::setClasesIds);
                });

        // De PostDTO a Model
        modelMapper.typeMap(TrabajadorPostDTO.class, Trabajador.class).addMappings(mapper -> {
            mapper.skip(Trabajador::setId);
            mapper.skip(Trabajador::setClases);
            mapper.map(src -> Usuario.TipoUsuario.TRABAJADOR, Trabajador::setTipoUsuario);
        });

        // De PutDTO a Model
        modelMapper.typeMap(TrabajadorPutDTO.class, Trabajador.class).addMappings(mapper -> {
            mapper.skip(Trabajador::setClases);
            mapper.skip(Trabajador::setTipoUsuario);
        });
    }

    private void configurarMapeosClase(ModelMapper modelMapper) {
        // De Model a GetDTO (mapeo básico, relaciones se saltan)
        modelMapper.typeMap(Clase.class, ClaseGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Clase::getId, ClaseGetDTO::setId);
                    mapper.map(Clase::getNombre, ClaseGetDTO::setNombre);
                    mapper.map(Clase::getPrecio, ClaseGetDTO::setPrecio);
                    mapper.map(Clase::getHoraInicio, ClaseGetDTO::setHoraInicio);
                    mapper.map(Clase::getHoraFin, ClaseGetDTO::setHoraFin);
                    mapper.map(Clase::getCapacidadMaxima, ClaseGetDTO::setCapacidadMaxima);
                    mapper.map(Clase::getSala, ClaseGetDTO::setSala);
                    mapper.map(Clase::getDias, ClaseGetDTO::setDias);
                    mapper.map(Clase::isActiva, ClaseGetDTO::setActiva);
                    mapper.skip(ClaseGetDTO::setInstructorId); // Se manejará manualmente
                    mapper.skip(ClaseGetDTO::setInstructorNombre); // Se manejará manualmente
                    mapper.skip(ClaseGetDTO::setTotalInscritos); // Se manejará manualmente
                });

        // De PostDTO a Model (saltar relaciones y campos no necesarios)
        modelMapper.typeMap(ClasePostDTO.class, Clase.class)
                .addMappings(mapper -> {
                    mapper.skip(Clase::setId);
                    mapper.skip(Clase::setInstructor); // Se asignará manualmente desde instructorId
                    mapper.skip(Clase::setInscripciones); // Relación ignorada
                });

        // De PutDTO a Model (saltar relaciones)
        modelMapper.typeMap(ClasePutDTO.class, Clase.class)
                .addMappings(mapper -> {
                    mapper.skip(Clase::setInstructor); // Se asignará manualmente desde instructorId
                    mapper.skip(Clase::setInscripciones); // Relación ignorada
                });
    }

    private void configurarMapeosModificacionClase(ModelMapper modelMapper) {
        // De Model a GetDTO (mapeo básico, relaciones se saltan)
        modelMapper.typeMap(ModificacionClase.class, ModificacionClaseGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(ModificacionClase::getId, ModificacionClaseGetDTO::setId);
                    mapper.map(ModificacionClase::getPrecio, ModificacionClaseGetDTO::setPrecio);
                    mapper.map(ModificacionClase::getHoraInicio, ModificacionClaseGetDTO::setHoraInicio);
                    mapper.map(ModificacionClase::getHoraFin, ModificacionClaseGetDTO::setHoraFin);
                    mapper.map(ModificacionClase::getCapacidadMaxima, ModificacionClaseGetDTO::setCapacidadMaxima);
                    mapper.map(ModificacionClase::getSala, ModificacionClaseGetDTO::setSala);
                    mapper.map(ModificacionClase::getDias, ModificacionClaseGetDTO::setDias);
                    mapper.map(ModificacionClase::getFechaModificacion, ModificacionClaseGetDTO::setFechaModificacion);
                    mapper.map(ModificacionClase::getComentario, ModificacionClaseGetDTO::setComentario);
                    mapper.skip(ModificacionClaseGetDTO::setClaseId); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setClaseNombre); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setInstructorId); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setInstructorNombre); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setTotalInscritos); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setModificadoPorId); // Se manejará manualmente
                    mapper.skip(ModificacionClaseGetDTO::setModificadoPorNombre); // Se manejará manualmente
                });

        // De PostDTO a Model (saltar relaciones y campos no necesarios)
        modelMapper.typeMap(ModificacionClasePostDTO.class, ModificacionClase.class)
                .addMappings(mapper -> {
                    mapper.skip(ModificacionClase::setId);
                    mapper.skip(ModificacionClase::setClase); // Se asignará manualmente desde claseId
                    mapper.skip(ModificacionClase::setInstructor); // Se asignará manualmente desde instructorId
                    mapper.skip(ModificacionClase::setModificadoPor); // Se asignará manualmente desde modificadoPor
                    mapper.skip(ModificacionClase::setFechaModificacion); // Se genera automáticamente
                });

        // De PutDTO a Model (saltar relaciones)
        modelMapper.typeMap(ModificacionClasePutDTO.class, ModificacionClase.class)
                .addMappings(mapper -> {
                    mapper.skip(ModificacionClase::setClase); // Se asignará manualmente si se proporciona claseId
                    mapper.skip(ModificacionClase::setInstructor); // Se asignará manualmente si se proporciona instructorId
                    mapper.skip(ModificacionClase::setModificadoPor); // Se asignará manualmente si se proporciona modificadoPor
                    mapper.skip(ModificacionClase::setFechaModificacion); // No se actualiza
                });
    }

    private void configurarMapeosInscripcion(ModelMapper modelMapper) {
        // De Model a GetDTO (mapeo básico, relaciones se saltan)
        modelMapper.typeMap(Inscripcion.class, InscripcionGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Inscripcion::getId, InscripcionGetDTO::setId);
                    mapper.map(Inscripcion::getFechaRegistro, InscripcionGetDTO::setFechaRegistro);
                    mapper.map(Inscripcion::getEstadoPago, InscripcionGetDTO::setEstadoPago);
                    mapper.skip(InscripcionGetDTO::setClienteId);         // Se manejará manualmente
                    mapper.skip(InscripcionGetDTO::setClienteNombre);    // Se manejará manualmente
                    mapper.skip(InscripcionGetDTO::setClaseId);           // Se manejará manualmente
                    mapper.skip(InscripcionGetDTO::setClaseNombre);       // Se manejará manualmente
                    mapper.skip(InscripcionGetDTO::setPagoId);           // Se manejará manualmente
                });

        // De PostDTO a Model (saltar relaciones y campos no necesarios)
        modelMapper.typeMap(InscripcionPostDTO.class, Inscripcion.class)
                .addMappings(mapper -> {
                    mapper.skip(Inscripcion::setId);
                    mapper.skip(Inscripcion::setCliente);   // Se asignará manualmente desde clienteId
                    mapper.skip(Inscripcion::setClase);    // Se asignará manualmente desde claseId
                    mapper.skip(Inscripcion::setPago);      // Se asignará manualmente desde pagoId (si existe)
                    mapper.skip(Inscripcion::setFechaRegistro); // Se genera automáticamente con @PrePersist
                });

        // De PutDTO a Model (saltar relaciones)
        modelMapper.typeMap(InscripcionPutDTO.class, Inscripcion.class)
                .addMappings(mapper -> {
                    mapper.skip(Inscripcion::setCliente);   // Se asignará manualmente desde clienteId (si se proporciona)
                    mapper.skip(Inscripcion::setClase);    // Se asignará manualmente desde claseId (si se proporciona)
                    mapper.skip(Inscripcion::setPago);      // Se asignará manualmente desde pagoId (si se proporciona)
                    mapper.skip(Inscripcion::setFechaRegistro); // No se actualiza
                });
    }

    private void configurarMapeosPago(ModelMapper modelMapper) {
        // De Model a GetDTO
        modelMapper.typeMap(Pago.class, PagoGetDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Pago::getId, PagoGetDTO::setId);
                    mapper.map(Pago::getMonto, PagoGetDTO::setMonto);
                    mapper.map(Pago::getFechaPago, PagoGetDTO::setFechaPago);
                    mapper.map(Pago::getEstadoPago, PagoGetDTO::setEstadoPago);
                    mapper.map(Pago::getComentario, PagoGetDTO::setComentario);
                    mapper.skip(PagoGetDTO::setClienteId); // Se manejará manualmente
                    mapper.skip(PagoGetDTO::setClienteNombre); // Se manejará manualmente
                    mapper.skip(PagoGetDTO::setInscripcionesIds); // Se manejará manualmente
                });

        // De PostDTO a Model
        modelMapper.typeMap(PagoPostDTO.class, Pago.class)
                .addMappings(mapper -> {
                    mapper.skip(Pago::setId);
                    mapper.skip(Pago::setCliente); // Se asignará manualmente desde clienteId
                    mapper.skip(Pago::setInscripciones); // Se asignará manualmente desde inscripcionesIds
                    mapper.skip(Pago::setFechaPago); // Se genera automáticamente según estadoPago
                });

        // De PutDTO a Model
        modelMapper.typeMap(PagoPutDTO.class, Pago.class)
                .addMappings(mapper -> {
                    mapper.skip(Pago::setCliente); // Se asignará manualmente desde clienteId (si se proporciona)
                    mapper.skip(Pago::setInscripciones); // Se asignará manualmente desde inscripcionesIds (si se proporciona)
                });
    }


}
