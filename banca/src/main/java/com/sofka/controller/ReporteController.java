package com.sofka.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	private ReporteService reporteService;

	public ReporteController(ReporteService reporteService) {
		this.reporteService = reporteService;
	}

	@GetMapping
	public ResponseEntity<?> generarReporteEstadoCuenta(@RequestParam(name = "clienteId") Long clienteId,
			@RequestParam(name = "fechaInicio") LocalDateTime fechaInicio,
			@RequestParam(name = "fechaFin") LocalDateTime fechaFin) {

		return reporteService.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
	}

}
