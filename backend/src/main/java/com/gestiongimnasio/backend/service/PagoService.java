package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;

import java.util.List;

public interface PagoService {
    List<PagoGetDTO> findAllPagos();
    PagoGetDTO findPagoById(Long id)  ;
    PagoGetDTO savePago(PagoPostDTO pagoPostDTO)  ;
    PagoGetDTO updatePago(Long id, PagoPutDTO pagoPutDTO)  ;
    void deletePago(Long id)  ;
    List<PagoGetDTO> findByCliente(Long clienteId);
    PagoGetDTO procesarPago(Long pagoId)  ;
    PagoGetDTO cancelarPago(Long pagoId)  ;
}