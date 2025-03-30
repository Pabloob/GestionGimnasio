package com.gestiongimnasio.backend.controller;

import java.util.List;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.service.TrabajadorService;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;
    private final UsuarioService usuarioService;


    public TrabajadorController(TrabajadorService trabajadorService, UsuarioService usuarioService) {
        this.trabajadorService = trabajadorService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<TrabajadorGetDTO>> getAll() {
        List<TrabajadorGetDTO> trabajadores = trabajadorService.getAll();
        return ResponseEntity.ok(trabajadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorGetDTO> getById(@PathVariable Long id) {
        TrabajadorGetDTO trabajador = trabajadorService.getById(id);
        return ResponseEntity.ok(trabajador);
    }

    @GetMapping("/tipos/{tipo}")
    public ResponseEntity<List<TrabajadorGetDTO>> getByTipo(@PathVariable Trabajador.TipoTrabajador tipo) {
        List<TrabajadorGetDTO> trabajadores = trabajadorService.getByTipo(tipo);
        return ResponseEntity.ok(trabajadores);
    }

    @GetMapping("/clases/{id}")
    public ResponseEntity<List<ClaseGetDTO>> getClasesImpartidas(@PathVariable Long id) {
        List<ClaseGetDTO> clases = trabajadorService.getClasesImpartidas(id);
        return ResponseEntity.ok(clases);
    }

    @PostMapping
    public ResponseEntity<TrabajadorGetDTO> save(@RequestBody TrabajadorPostDTO trabajadorDTO) {
        if (usuarioService.existsByCorreo(trabajadorDTO.getCorreo())) {
            return ResponseEntity.badRequest().build();
        }

        TrabajadorGetDTO nuevoTrabajador = trabajadorService.save(trabajadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTrabajador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorGetDTO> update(@PathVariable Long id, @RequestBody TrabajadorPutDTO trabajadorDTO) {
        TrabajadorGetDTO updatedTrabajador = trabajadorService.update(id, trabajadorDTO);
        return ResponseEntity.ok(updatedTrabajador);
    }

}