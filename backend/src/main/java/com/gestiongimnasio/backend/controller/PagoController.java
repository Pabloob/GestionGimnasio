package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.PagoDTO;
import com.gestiongimnasio.backend.model.Pago;
import com.gestiongimnasio.backend.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        List<PagoDTO> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> getPagoById(@PathVariable Integer id) {
        PagoDTO pago = pagoService.getPagoById(id);
        return ResponseEntity.ok(pago);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoDTO pagoDTO) {
        PagoDTO nuevoPago = pagoService.savePago(pagoDTO);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> updatePago(@PathVariable Integer id, @RequestBody PagoDTO pagoDTO) {
        PagoDTO updatedPago = pagoService.updatePago(id, pagoDTO);
        return ResponseEntity.ok(updatedPago);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePago(@PathVariable Integer id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA') or #clienteId == principal.id")
    public ResponseEntity<List<PagoDTO>> getPagosByCliente(@PathVariable Long clienteId) {
        List<PagoDTO> pagos = pagoService.getPagosByCliente(clienteId);
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<List<PagoDTO>> getPagosByEstado(@PathVariable Pago.EstadoPago estado) {
        List<PagoDTO> pagos = pagoService.getPagosByEstado(estado);
        return ResponseEntity.ok(pagos);
    }

    @PutMapping("/{id}/completar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> marcarComoCompletado(@PathVariable Integer id) {
        PagoDTO pago = pagoService.marcarComoCompletado(id);
        return ResponseEntity.ok(pago);
    }
}