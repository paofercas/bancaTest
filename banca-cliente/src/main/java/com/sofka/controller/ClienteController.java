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

import com.sofka.entity.Cliente;
import com.sofka.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController  {
	
	@Autowired
	private ClienteService clienteService;
	
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearCliente(@RequestBody @Valid Cliente cliente) {
    	if (cliente.getEdad() < 18|| cliente.getEdad() > 120) {
            return ResponseEntity.badRequest().body("La edad debe ser un número entero entre 18 y 100");
        }
    	
    	return clienteService.crearCliente(cliente);
    	
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }
    
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarCliente(
            @RequestBody @Valid Cliente cliente) {
       
        return  clienteService.actualizarCliente(cliente);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> eliminarCliente( @RequestBody @Valid Cliente cliente) {
        return clienteService.eliminarCliente(cliente);
    }

}
