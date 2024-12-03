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

import com.sofka.entity.Movimientos;
import com.sofka.service.MovimientosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
	
	@Autowired
	private MovimientosService movimientoService;
	
	
	public MovimientosController(MovimientosService movimientoService) {
        this.movimientoService= movimientoService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearMovimiento(@RequestBody @Valid Movimientos movimiento) {  
    	  	
    	return movimientoService.crearMovimiento(movimiento);
    }

    @GetMapping
    public List<Movimientos> listarMovimientos() {
        return movimientoService.listarMovimientos();
    }
    
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarMovimiento(
            @RequestBody @Valid Movimientos movimento) {
       
        return  movimientoService.actualizarMovimiento(movimento);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> eliminarMovimiento( @RequestBody @Valid Movimientos movimiento) {
        return movimientoService.eliminarMovimiento(movimiento);
    }


}
