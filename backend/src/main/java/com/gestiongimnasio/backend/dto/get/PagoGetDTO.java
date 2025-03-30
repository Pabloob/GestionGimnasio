package com.gestiongimnasio.backend.dto.get;

import com.gestiongimnasio.backend.model.Pago;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PagoGetDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private List<Long> inscripcionesIds;
    private Double monto;
    private LocalDate fechaPago;
    private Pago.EstadoPago estadoPago;
    private String comentario;
}