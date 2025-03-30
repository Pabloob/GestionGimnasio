package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.InscripcionGetDTO;
import com.gestiongimnasio.backend.dto.post.InscripcionPostDTO;
import com.gestiongimnasio.backend.dto.put.InscripcionPutDTO;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.InscripcionService;
import com.gestiongimnasio.backend.utils.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final ClaseRepository claseRepository;
    private final PagoRepository pagoRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public InscripcionGetDTO getById(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada con ID: " + id));
        return toResponseDTO(inscripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionGetDTO> getByCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new BusinessException("Cliente no encontrado con ID: " + clienteId);
        }
        return inscripcionRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionGetDTO> getByClase(Long claseId) {
        if (!claseRepository.existsById(claseId)) {
            throw new BusinessException("Clase no encontrada con ID: " + claseId);
        }
        return inscripcionRepository.findByClaseId(claseId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionGetDTO> getAll() {
        return inscripcionRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionGetDTO save(InscripcionPostDTO inscripcionDTO) {
        validateInscripcionPostDTO(inscripcionDTO);

        Cliente cliente = clienteRepository.findById(inscripcionDTO.getClienteId())
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + inscripcionDTO.getClienteId()));

        Clase clase = claseRepository.findById(inscripcionDTO.getClaseId())
                .orElseThrow(() -> new BusinessException("Clase no encontrada con ID: " + inscripcionDTO.getClaseId()));

        validateClaseCapacity(clase);
        validateDuplicateInscripcion(cliente.getId(), clase.getId());

        Inscripcion inscripcion = modelMapper.map(inscripcionDTO, Inscripcion.class);
        inscripcion.setCliente(cliente);
        inscripcion.setClase(clase);
        inscripcion.setEstadoPago(false);

        handlePagoAssociation(inscripcionDTO, inscripcion);

        return toResponseDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public InscripcionGetDTO update(Long id, InscripcionPutDTO inscripcionDTO) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada con ID: " + id));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(inscripcionDTO, inscripcion);

        handlePagoUpdate(inscripcionDTO, inscripcion);

        return toResponseDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public void delete(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada con ID: " + id));
        inscripcionRepository.delete(inscripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsInscripcion(Long clienteId, Long claseId) {
        return inscripcionRepository.existsByClienteIdAndClaseId(clienteId, claseId);
    }

    @Override
    public InscripcionGetDTO confirmarPago(Long inscripcionId, Long pagoId) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada con ID: " + inscripcionId));

        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + pagoId));

        validatePagoClienteMatch(pago, inscripcion.getCliente().getId());

        inscripcion.setPago(pago);
        inscripcion.setEstadoPago(true);

        return toResponseDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public InscripcionGetDTO confirmarAsistencia(Long inscripcionId) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new BusinessException("Inscripción no encontrada con ID: " + inscripcionId));

        if (!inscripcion.getEstadoPago()) {
            throw new BusinessException("No se puede confirmar asistencia sin pago confirmado");
        }

        inscripcion.setAsistio(true);
        return toResponseDTO(inscripcionRepository.save(inscripcion));
    }

    private void validateInscripcionPostDTO(InscripcionPostDTO inscripcionDTO) {
        if (inscripcionDTO == null) {
            throw new IllegalArgumentException("Datos de inscripción no pueden ser nulos");
        }
    }

    private void validateClaseCapacity(Clase clase) {
        long inscritos = inscripcionRepository.countByClaseId(clase.getId());
        if (inscritos >= clase.getCapacidadMaxima()) {
            throw new BusinessException("La clase ha alcanzado su capacidad máxima");
        }
    }

    private void validateDuplicateInscripcion(Long clienteId, Long claseId) {
        if (existsInscripcion(clienteId, claseId)) {
            throw new BusinessException("El cliente ya está inscrito en esta clase");
        }
    }

    private void handlePagoAssociation(InscripcionPostDTO inscripcionDTO, Inscripcion inscripcion) {
        if (inscripcionDTO.getPagoId() != null) {
            Pago pago = pagoRepository.findById(inscripcionDTO.getPagoId())
                    .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + inscripcionDTO.getPagoId()));
            validatePagoClienteMatch(pago, inscripcionDTO.getClienteId());
            inscripcion.setPago(pago);
            inscripcion.setEstadoPago(true);
        }
    }

    private void handlePagoUpdate(InscripcionPutDTO inscripcionDTO, Inscripcion inscripcion) {
        if (inscripcionDTO.getPagoId() != null) {
            Pago pago = pagoRepository.findById(inscripcionDTO.getPagoId())
                    .orElseThrow(() -> new BusinessException("Pago no encontrado con ID: " + inscripcionDTO.getPagoId()));
            validatePagoClienteMatch(pago, inscripcion.getCliente().getId());
            inscripcion.setPago(pago);
            inscripcion.setEstadoPago(true);
        } else if (inscripcionDTO.getEstadoPago() != null) {
            inscripcion.setEstadoPago(inscripcionDTO.getEstadoPago());
        }
    }

    private void validatePagoClienteMatch(Pago pago, Long clienteId) {
        if (!pago.getCliente().getId().equals(clienteId)) {
            throw new BusinessException("El pago no corresponde al cliente de la inscripción");
        }
        if (pago.getEstadoPago() != Pago.EstadoPago.COMPLETADO) {
            throw new BusinessException("El pago no está completado");
        }
    }

    private InscripcionGetDTO toResponseDTO(Inscripcion inscripcion) {
        InscripcionGetDTO dto = modelMapper.map(inscripcion, InscripcionGetDTO.class);

        dto.setClienteId(inscripcion.getCliente().getId());
        dto.setClienteNombre(inscripcion.getCliente().getNombre());

        dto.setClaseId(inscripcion.getClase().getId());
        dto.setClaseNombre(inscripcion.getClase().getNombre());

        if (inscripcion.getPago() != null) {
            dto.setPagoId(inscripcion.getPago().getId());
        }

        return dto;
    }
}