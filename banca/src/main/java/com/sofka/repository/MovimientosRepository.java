package com.sofka.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofka.entity.Movimientos;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {
	
	List<Movimientos> findByFechaBetweenAndCuentaClienteId(LocalDateTime fechaInicio, LocalDateTime fechaFin, Long clienteId);

}
