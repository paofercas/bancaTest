package com.sofka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.dto.CuentaDTO;
import com.sofka.entity.Cuenta;
import com.sofka.event.ClienteEventConsumer;
import com.sofka.event.ClienteEventProducer;
import com.sofka.service.CuentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	ClienteEventProducer producer;
	
	@Autowired
	ClienteEventConsumer consumer;
	
	public CuentaController(CuentaService cuentaService, ClienteEventProducer producer, ClienteEventConsumer consumer) {
        this.cuentaService = cuentaService;
        this.producer= producer;
        this.consumer = consumer;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearCuenta(@RequestBody @Valid CuentaDTO cuentaDTO) {  
    	
    	producer.solicitarClienteporNombre(cuentaDTO);
    	return ResponseEntity.ok("Solicitud de creación de cuenta en proceso.");
    }

    @GetMapping
    public List<Cuenta> listarCuentas() {
        return cuentaService.listarCuentas();
    }
    
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarCuenta(
            @RequestBody @Valid Cuenta cuenta) {
       
        return  cuentaService.actualizarCuenta(cuenta);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> eliminarCuenta( @RequestBody @Valid Cuenta cuenta) {
        return cuentaService.eliminarCuenta(cuenta);
    }

}
