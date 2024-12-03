package com.sofka.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;
import com.sofka.dto.ErrorDto;
import com.sofka.dto.ReporteCuenta;
import com.sofka.dto.ResponseDto;
import com.sofka.entity.Cuenta;
import com.sofka.entity.Movimientos;
import com.sofka.event.ClienteEventConsumer;

@Service
public class ReporteService {
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private MovimientosService movimientoService;
	
	@Autowired
	private ClienteEventConsumer clienteConsumer;
	
	public ResponseEntity<?> generarReporteEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
	    // Obtener cliente
		CuentaDTO cliente = clienteConsumer.obtenerCliente(clienteId);
	    if (cliente == null) {
	        return generarError("400", "El cliente no existe");
	    }

	    List<Cuenta> cuentas = cuentaService.buscarPorClienteId(clienteId);
	    List<Movimientos> movimientos = movimientoService.findByFechaBetweenAndCuentaClienteId(fechaInicio, fechaFin, clienteId);

	    Map<String, List<Movimientos>> movimientosPorCuenta = extraerMovimientosPorCuenta(movimientos);

	    // Crear el reporte de cuentas
	    List<ReporteCuenta> reporteCuentas = cuentas.stream()
	        .map(cuenta -> new ReporteCuenta(cuenta, movimientosPorCuenta.getOrDefault(cuenta.getNumeroCuenta(), new ArrayList<>())))
	        .collect(Collectors.toList());

	    // Retornar el reporte
	    ResponseDto<List<ReporteCuenta>> response = new ResponseDto<>(reporteCuentas);
	    return ResponseEntity.ok(response);
	}

	private ResponseEntity<?> generarError(String code, String message) {
	    ErrorDto error = new ErrorDto(code, message);
	    ResponseDto<?> errorResponse = new ResponseDto<>(error);
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	public Map<String, List<Movimientos>> extraerMovimientosPorCuenta(List<Movimientos> movimientos) {
	    return movimientos.stream()
	        .collect(Collectors.groupingBy(movimiento -> movimiento.getCuenta().getNumeroCuenta()));
	}

	
}
