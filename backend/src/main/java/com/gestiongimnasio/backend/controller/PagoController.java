package com.gestiongimnasio.backend.controller;

import com.gestiongimnasio.backend.dto.get.PagoGetDTO;
import com.gestiongimnasio.backend.dto.post.PagoPostDTO;
import com.gestiongimnasio.backend.dto.put.PagoPutDTO;
import com.gestiongimnasio.backend.model.Pago;
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
    public ResponseEntity<List<PagoGetDTO>> getAll() {
        List<PagoGetDTO> pagos = pagoService.getAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<PagoGetDTO>> getByCliente(@PathVariable Long id) {
        List<PagoGetDTO> pagos = pagoService.getByCliente(id);
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoGetDTO>> getByEstado(@PathVariable Pago.EstadoPago estado) {
        List<PagoGetDTO> pagos = pagoService.getByEstado(estado);
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoGetDTO> getById(@PathVariable Long id) {
        PagoGetDTO pago = pagoService.getById(id);
        return ResponseEntity.ok(pago);
    }

    @PostMapping
    public ResponseEntity<PagoGetDTO> save(@RequestBody PagoPostDTO pagoDTO) {
        PagoGetDTO nuevoPago = pagoService.save(pagoDTO);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoGetDTO> update(@PathVariable Long id, @RequestBody PagoPutDTO pagoDTO) {
        PagoGetDTO updatedPago = pagoService.update(id, pagoDTO);
        return ResponseEntity.ok(updatedPago);
    }

    @PutMapping("/completar/{id}")
    public ResponseEntity<PagoGetDTO> marcarComoCompletado(@PathVariable Long id) {
        PagoGetDTO pago = pagoService.marcarComoCompletado(id);
        return ResponseEntity.ok(pago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}