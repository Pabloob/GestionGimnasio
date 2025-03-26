package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.PagoDTO;
import com.gestiongimnasio.backend.model.Pago;
import com.gestiongimnasio.backend.repository.PagoRepository;
import com.gestiongimnasio.backend.service.PagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PagoDTO savePago(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);

        Pago pagoGuardado = pagoRepository.save(pago);

        return modelMapper.map(pagoGuardado, PagoDTO.class);
    }

    @Override
    public List<PagoDTO> getAllPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PagoDTO getPagoById(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return modelMapper.map(pago, PagoDTO.class);
    }

    @Override
    public PagoDTO updatePago(Integer id, PagoDTO pagoDTO) {
        Pago pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        // Actualizar campos
        modelMapper.map(pagoDTO, pagoExistente);

        // Guardar cambios
        Pago pagoActualizado = pagoRepository.save(pagoExistente);
        return modelMapper.map(pagoActualizado, PagoDTO.class);
    }

    @Override
    public void deletePago(Integer id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public List<PagoDTO> getPagosByCliente(Long clienteId) {
        return pagoRepository.findByClienteId(clienteId)
                .stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoDTO> getPagosByEstado(Pago.EstadoPago estado) {
        return pagoRepository.findByEstadoPago(estado)
                .stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PagoDTO marcarComoCompletado(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago != null) {
            pago.setEstadoPago(Pago.EstadoPago.COMPLETADO);
            pago.setFechaPago(LocalDate.now());
            Pago pagoActualizado = pagoRepository.save(pago);
            return modelMapper.map(pagoActualizado, PagoDTO.class);

        }
        return null;

    }
}