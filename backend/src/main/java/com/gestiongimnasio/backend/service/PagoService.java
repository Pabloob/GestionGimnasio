package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.model.Pago;

import java.util.List;

public interface PagoService {
    PagoGetDTO getById(Long id);

    List<PagoGetDTO> getByCliente(Long clienteId);

    List<PagoGetDTO> getByEstado(Pago.EstadoPago estado);

    List<PagoGetDTO> getAll();

    PagoGetDTO save(PagoPostDTO pagoDTO);

    PagoGetDTO update(Long id, PagoPutDTO pagoDTO);

    void delete(Long id);

    PagoGetDTO marcarComoCompletado(Long id);

}