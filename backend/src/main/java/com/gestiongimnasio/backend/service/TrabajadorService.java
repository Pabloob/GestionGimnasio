package com.gestiongimnasio.backend.service;

import com.gestiongimnasio.backend.dto.get.ClaseGetDTO;
import com.gestiongimnasio.backend.dto.get.TrabajadorGetDTO;
import com.gestiongimnasio.backend.dto.post.TrabajadorPostDTO;
import com.gestiongimnasio.backend.dto.put.TrabajadorPutDTO;
import com.gestiongimnasio.backend.model.Trabajador;

import java.util.List;

public interface TrabajadorService {
    TrabajadorGetDTO getById(Long id);

    List<TrabajadorGetDTO> getAll();
    List<TrabajadorGetDTO> getByTipo(Trabajador.TipoTrabajador tipo);
    List<ClaseGetDTO> getClasesImpartidas(Long instructorId);

    TrabajadorGetDTO save(TrabajadorPostDTO registroDTO);

    TrabajadorGetDTO update(Long id, TrabajadorPutDTO updateDTO);


}