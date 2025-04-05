package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.mappers.PagoMapper;
import com.gestiongimnasio.backend.model.*;
import com.gestiongimnasio.backend.repository.*;
import com.gestiongimnasio.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;
    private final PagoMapper pagoMapper;

    @Override
    public List<PagoGetDTO> findAllPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public PagoGetDTO findPagoById(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return pagoMapper.toGetDto(pago);
    }

    @Override
    public PagoGetDTO savePago(PagoPostDTO pagoPostDTO) {
        // Validar existencia del cliente
        Cliente cliente = clienteRepository.findById(pagoPostDTO.getClienteId()).orElseThrow();
        // Validar monto positivo
        if (pagoPostDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser positivo");
        }

        // Crear el pago
        Pago pago = pagoMapper.toEntity(pagoPostDTO);
        pago.setCliente(cliente);

        Pago savedPago = pagoRepository.save(pago);
        return pagoMapper.toGetDto(savedPago);
    }

    @Override
    public PagoGetDTO updatePago(Long id, PagoPutDTO pagoPutDTO) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        // Validar entidades relacionadas si se están actualizando
        if (pagoPutDTO.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(pagoPutDTO.getClienteId()).orElseThrow();
            pago.setCliente(cliente);
        }

        // Validar monto si se está actualizando
        if (pagoPutDTO.getMonto() != null && pagoPutDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser positivo");
        }

        pagoMapper.updateFromDto(pagoPutDTO, pago);
        Pago updatedPago = pagoRepository.save(pago);
        return pagoMapper.toGetDto(updatedPago);
    }

    @Override
    public void deletePago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }

    @Override
    public List<PagoGetDTO> findByCliente(Long clienteId) {
        return pagoRepository.findByClienteId(clienteId)
                .stream()
                .map(pagoMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public PagoGetDTO procesarPago(Long pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + pagoId));

        pago.setFechaPago(LocalDate.now());
        pago.setPagado(true);

        Pago processedPago = pagoRepository.save(pago);
        return pagoMapper.toGetDto(processedPago);
    }

    @Override
    public PagoGetDTO cancelarPago(Long pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + pagoId));

        pago.setPagado(false);

        Pago canceledPago = pagoRepository.save(pago);
        return pagoMapper.toGetDto(canceledPago);
    }

}