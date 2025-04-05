package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<PagoGetDTO>> getAllPagos() {
        List<PagoGetDTO> pagos = pagoService.findAllPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoGetDTO> getPagoById(@PathVariable Long id) {
        PagoGetDTO pago = pagoService.findPagoById(id);
        return ResponseEntity.ok(pago);
    }

    @PostMapping
    public ResponseEntity<PagoGetDTO> createPago(@RequestBody PagoPostDTO pagoPostDTO) {
        PagoGetDTO nuevoPago = pagoService.savePago(pagoPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoGetDTO> updatePago(@PathVariable Long id,
                                                 @RequestBody PagoPutDTO pagoPutDTO) {
        PagoGetDTO pagoActualizado = pagoService.updatePago(id, pagoPutDTO);
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagoGetDTO>> getPagosByCliente(@PathVariable Long clienteId) {
        List<PagoGetDTO> pagos = pagoService.findByCliente(clienteId);
        return ResponseEntity.ok(pagos);
    }

    @PatchMapping("/{id}/procesar")
    public ResponseEntity<Void> procesarPago(@PathVariable Long id) {
        pagoService.procesarPago(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPago(@PathVariable Long id) {
        pagoService.cancelarPago(id);
        return ResponseEntity.noContent().build();
    }
}