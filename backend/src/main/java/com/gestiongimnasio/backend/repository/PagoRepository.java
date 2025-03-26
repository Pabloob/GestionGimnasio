package com.gestiongimnasio.backend.repository;

import com.gestiongimnasio.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByClienteId(Long clienteId);
    List<Pago> findByEstadoPago(Pago.EstadoPago estado);
}