package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.mappers.TrabajadorMapper;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClaseHorarioRepository claseHorarioRepository;
    private final TrabajadorMapper trabajadorMapper;

    @Override
    public List<TrabajadorGetDTO> findAllTrabajadores() {
        return trabajadorRepository.findAll()
                .stream()
                .map(trabajadorMapper::toTrabajadorGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrabajadorGetDTO findTrabajadorById(Long id) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + id));
        return trabajadorMapper.toTrabajadorGetDTO(trabajador);
    }

    @Override
    public TrabajadorGetDTO saveTrabajador(TrabajadorPostDTO trabajadorPostDTO) {

        if (trabajadorRepository.existsByCorreo(trabajadorPostDTO.getUsuarioPostDTO().getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (trabajadorPostDTO.getHoraFin().isBefore(trabajadorPostDTO.getHoraInicio())) {
            throw new RuntimeException("La hora de fin debe ser posterior a la hora de inicio");
        }

        trabajadorPostDTO.getUsuarioPostDTO().setContrasena(passwordEncoder.encode(trabajadorPostDTO.getUsuarioPostDTO().getContrasena()));

        Trabajador trabajador = trabajadorMapper.fromTrabajadorPostDTO(trabajadorPostDTO);
        trabajador.setTipoUsuario(Usuario.TipoUsuario.TRABAJADOR);

        Trabajador savedTrabajador = trabajadorRepository.save(trabajador);
        return trabajadorMapper.toTrabajadorGetDTO(savedTrabajador);
    }

    @Override
    public TrabajadorGetDTO updateTrabajador(Long id, TrabajadorPutDTO trabajadorPutDTO) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + id));

        if (trabajadorPutDTO.getUsuarioPutDTO().getCorreo() != null &&
                !trabajadorPutDTO.getUsuarioPutDTO().getCorreo().equals(trabajador.getCorreo()) &&
                trabajadorRepository.existsByCorreo(trabajadorPutDTO.getUsuarioPutDTO().getCorreo())) {
            throw new RuntimeException("El nuevo correo ya está registrado");
        }

        if (trabajadorPutDTO.getHoraFin() != null && trabajadorPutDTO.getHoraInicio() != null &&
                trabajadorPutDTO.getHoraFin().isBefore(trabajadorPutDTO.getHoraInicio())) {
            throw new RuntimeException("La hora de fin debe ser posterior a la hora de inicio");
        }

        if (trabajadorPutDTO.getUsuarioPutDTO().getContrasena() != null && !trabajadorPutDTO.getUsuarioPutDTO().getContrasena().isEmpty()) {
            trabajadorPutDTO.getUsuarioPutDTO().setContrasena(passwordEncoder.encode(trabajadorPutDTO.getUsuarioPutDTO().getContrasena()));
        }

        trabajadorMapper.fromTrabajadorPutDTO(trabajadorPutDTO, trabajador);

        Trabajador updatedTrabajador = trabajadorRepository.save(trabajador);
        return trabajadorMapper.toTrabajadorGetDTO(updatedTrabajador);
    }

    @Override
    public void deleteTrabajador(Long id) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + id));

        if (claseHorarioRepository.existsByInstructorId(id)) {
            throw new RuntimeException("No se puede eliminar un trabajador con reservas asignadas");
        }

        trabajadorRepository.delete(trabajador);
    }

    @Override
    public List<TrabajadorGetDTO> findByTipo(String tipoTrabajador) {
        try {
            Trabajador.TipoTrabajador tipo = Trabajador.TipoTrabajador.valueOf(tipoTrabajador.toUpperCase());
            return trabajadorRepository.findByTipoTrabajador(tipo)
                    .stream()
                    .map(trabajadorMapper::toTrabajadorGetDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de trabajador no válido: " + tipoTrabajador);
        }
    }

    @Override
    public boolean estaDisponible(Long trabajadorId, DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin) {
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + trabajadorId));

        boolean dentroHorarioLaboral = !horaInicio.isBefore(trabajador.getHoraInicio()) &&
                !horaFin.isAfter(trabajador.getHoraFin());

        boolean sinReservas = claseHorarioRepository
                .findByInstructorIdAndDiaSemana(trabajadorId, dia)
                .stream()
                .noneMatch(reserva -> horariosSeSolapan(
                        reserva.getHoraInicio(),
                        reserva.getHoraFin(),
                        horaInicio,
                        horaFin
                ));

        return dentroHorarioLaboral && sinReservas;
    }

    private boolean horariosSeSolapan(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        return inicio1.isBefore(fin2) && fin1.isAfter(inicio2);
    }
}