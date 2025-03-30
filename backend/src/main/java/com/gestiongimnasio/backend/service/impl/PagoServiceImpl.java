package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.PagoService;
import com.gestiongimnasio.backend.utils.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public PagoGetDTO getById(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + id));
        return toResponseDTO(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoGetDTO> getByCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new BusinessException("Cliente no encontrado con ID: " + clienteId);
        }
        return pagoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoGetDTO> getByEstado(Pago.EstadoPago estado) {
        if (estado == null) {
            throw new IllegalArgumentException("Estado de pago no puede ser nulo");
        }
        return pagoRepository.findByEstadoPago(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoGetDTO> getAll() {
        return pagoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoGetDTO save(PagoPostDTO pagoDTO) {
        validatePagoPostDTO(pagoDTO);

        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        pago.setCliente(clienteRepository.getReferenceById(pagoDTO.getClienteId()));
        pago.setEstadoPago(Pago.EstadoPago.PENDIENTE);
        pago.setFechaPago(null);

        handleInscripciones(pagoDTO, pago);

        return toResponseDTO(pagoRepository.save(pago));
    }

    @Override
    public PagoGetDTO update(Long id, PagoPutDTO pagoDTO) {
        if (!id.equals(pagoDTO.getId())) {
            throw new IllegalArgumentException("ID de pago no coincide");
        }

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + id));

        validatePagoPutDTO(pagoDTO);
        updatePagoFields(pagoDTO, pago);
        handleInscripcionesUpdate(pagoDTO, pago);

        return toResponseDTO(pagoRepository.save(pago));
    }

    @Override
    public void delete(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + id));

        pago.getInscripciones().forEach(inscripcion -> inscripcion.setPago(null));
        pagoRepository.delete(pago);
    }

    @Override
    public PagoGetDTO marcarComoCompletado(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + id));

        validatePagoCompletado(pago);

        pago.setEstadoPago(Pago.EstadoPago.COMPLETADO);
        pago.setFechaPago(LocalDate.now());
        pago.getInscripciones().forEach(inscripcion -> inscripcion.setEstadoPago(true));

        return toResponseDTO(pagoRepository.save(pago));
    }

    private void validatePagoPostDTO(PagoPostDTO pagoDTO) {
        if (pagoDTO == null) {
            throw new IllegalArgumentException("Datos de pago no pueden ser nulos");
        }
        if (!clienteRepository.existsById(pagoDTO.getClienteId())) {
            throw new BusinessException("Cliente no encontrado con ID: " + pagoDTO.getClienteId());
        }
        if (pagoDTO.getMonto() <= 0) {
            throw new BusinessException("El monto del pago debe ser positivo");
        }
    }

    private void handleInscripciones(PagoPostDTO pagoDTO, Pago pago) {
        if (pagoDTO.getInscripcionesIds() != null && !pagoDTO.getInscripcionesIds().isEmpty()) {
            List<Inscripcion> inscripciones = inscripcionRepository.findAllById(pagoDTO.getInscripcionesIds());

            validateInscripciones(inscripciones, pagoDTO.getInscripcionesIds(), pagoDTO.getClienteId());

            inscripciones.forEach(inscripcion -> inscripcion.setPago(pago));
            pago.setInscripciones(inscripciones);
        }
    }

    private void validatePagoPutDTO(PagoPutDTO pagoDTO) {
        if (pagoDTO.getMonto() != null && pagoDTO.getMonto() <= 0) {
            throw new BusinessException("El monto del pago debe ser positivo");
        }
    }

    private void updatePagoFields(PagoPutDTO pagoDTO, Pago pago) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(pagoDTO, pago);

        if (pagoDTO.getEstadoPago() != null) {
            pago.setEstadoPago(pagoDTO.getEstadoPago());
            if (pagoDTO.getEstadoPago() == Pago.EstadoPago.COMPLETADO && pago.getFechaPago() == null) {
                pago.setFechaPago(LocalDate.now());
            }
        }
    }

    private void handleInscripcionesUpdate(PagoPutDTO pagoDTO, Pago pago) {
        if (pagoDTO.getInscripcionesIds() != null) {
            pago.getInscripciones().forEach(inscripcion -> inscripcion.setPago(null));

            if (!pagoDTO.getInscripcionesIds().isEmpty()) {
                List<Inscripcion> nuevasInscripciones = inscripcionRepository.findAllById(pagoDTO.getInscripcionesIds());
                validateInscripciones(nuevasInscripciones, pagoDTO.getInscripcionesIds(), pago.getCliente().getId());
                nuevasInscripciones.forEach(inscripcion -> inscripcion.setPago(pago));
                pago.setInscripciones(nuevasInscripciones);
            } else {
                pago.setInscripciones(List.of());
            }
        }
    }

    private void validateInscripciones(List<Inscripcion> inscripciones, List<Long> inscripcionesIds, Long clienteId) {
        if (inscripciones.size() != inscripcionesIds.size()) {
            throw new BusinessException("Una o más inscripciones no fueron encontradas");
        }

        inscripciones.forEach(inscripcion -> {
            if (!inscripcion.getCliente().getId().equals(clienteId)) {
                throw new BusinessException("La inscripción con ID " + inscripcion.getId() +
                        " no pertenece al cliente con ID " + clienteId);
            }
            if (inscripcion.getPago() != null && !inscripcion.getPago().getId().equals(clienteId)) {
                throw new BusinessException("La inscripción con ID " + inscripcion.getId() +
                        " ya tiene un pago asociado");
            }
        });
    }

    private void validatePagoCompletado(Pago pago) {
        if (pago.getEstadoPago() == Pago.EstadoPago.COMPLETADO) {
            throw new BusinessException("El pago ya está completado");
        }
        if (pago.getEstadoPago() == Pago.EstadoPago.CANCELADO) {
            throw new BusinessException("No se puede completar un pago cancelado");
        }
    }

    private PagoGetDTO toResponseDTO(Pago pago) {
        PagoGetDTO dto = modelMapper.map(pago, PagoGetDTO.class);

        dto.setClienteId(pago.getCliente().getId());
        dto.setClienteNombre(pago.getCliente().getNombre());

        if (pago.getInscripciones() != null && !pago.getInscripciones().isEmpty()) {
            dto.setInscripcionesIds(
                    pago.getInscripciones().stream()
                            .map(Inscripcion::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}