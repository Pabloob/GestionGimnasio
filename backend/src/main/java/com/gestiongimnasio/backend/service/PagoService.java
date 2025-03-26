package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.PagoDTO;
import com.gestiongimnasio.backend.model.Pago;

import java.util.List;

public interface PagoService {
    PagoDTO savePago(PagoDTO pagoDTO);
    List<PagoDTO> getAllPagos();
    PagoDTO getPagoById(Integer id);
    PagoDTO updatePago(Integer id, PagoDTO pagoDTO);
    void deletePago(Integer id);
    List<PagoDTO> getPagosByCliente(Long clienteId);
    List<PagoDTO> getPagosByEstado(Pago.EstadoPago estado);
    PagoDTO marcarComoCompletado(Integer id);
}