package com.gestiongimnasio.backend.service.impl;

import com.gestiongimnasio.backend.dto.TrabajadorDTO;
import com.gestiongimnasio.backend.model.Cliente;
import com.gestiongimnasio.backend.model.Trabajador;
import com.gestiongimnasio.backend.repository.TrabajadorRepository;
import com.gestiongimnasio.backend.service.TrabajadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabajadorServiceImpl implements TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TrabajadorDTO saveTrabajador(TrabajadorDTO trabajadorDTO) {
        Trabajador trabajador = modelMapper.map(trabajadorDTO, Trabajador.class);

        Trabajador trabajadorGuardado = trabajadorRepository.save(trabajador);

        return modelMapper.map(trabajadorGuardado, TrabajadorDTO.class);
    }

    @Override
    public List<TrabajadorDTO> getAllTrabajadores() {
        return trabajadorRepository.findAll()
                .stream()
                .map(trabajador -> modelMapper.map(trabajador, TrabajadorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TrabajadorDTO getTrabajadorById(Long id) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }

    @Override
    public TrabajadorDTO updateTrabajador(Long id, TrabajadorDTO trabajadorDTO) {
        Trabajador trabajadorExistente = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        // Actualizar campos
        modelMapper.map(trabajadorDTO, trabajadorExistente);

        // Guardar cambios
        Trabajador trabajadorActualizado = trabajadorRepository.save(trabajadorExistente);
        return modelMapper.map(trabajadorActualizado, TrabajadorDTO.class);
    }

    @Override
    public void deleteTrabajador(Long id) {
        trabajadorRepository.deleteById(id);
    }

    @Override
    public List<TrabajadorDTO> findByTipo(Trabajador.TipoTrabajador tipo) {
        return trabajadorRepository.findByTipoTrabajador(tipo)
                .stream()
                .map(trabajador -> modelMapper.map(trabajador, TrabajadorDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public boolean existsByCorreo(String correo) {
        return trabajadorRepository.existsByCorreo(correo);
    }

    @Override
    public Long authenticateCliente(String correo, String contrasena) {
        Trabajador trabajador = trabajadorRepository.getTrabajadorByCorreoAndContrasena(correo,contrasena)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        return trabajador.getId();
    }
}