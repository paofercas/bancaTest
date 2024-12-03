package com.sofka.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.entity.Cliente;
import com.sofka.entity.Estado;
import com.sofka.entity.Genero;
import com.sofka.service.ClienteService;

public class ClienteControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ClienteService clienteService;
	
	@InjectMocks
	private ClienteController clienteController;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
	}

	@Test
	void testCrearCliente() throws Exception {
		Cliente cliente = crearClienteValido();
		
		 when(clienteService.crearCliente(any(Cliente.class)))
	        .thenReturn(ResponseEntity.ok().build());
		 
		mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cliente))).andExpect(status().isOk());
	}
	
	@Test
	void testCrearClienteDatosInvalidos() throws Exception {
	    Cliente clienteInvalido = crearClienteInValido();
	   
	    mockMvc.perform(post("/clientes")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(clienteInvalido)))
	            .andExpect(status().isBadRequest());
	}


	@Test
	void testListarClientes() throws Exception {
		Cliente cliente1 = crearClienteValido();
		Cliente cliente2 = crearClienteValido();

		when(clienteService.listarClientes()).thenReturn(Arrays.asList(cliente1, cliente2));

		mockMvc.perform(get("/clientes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].nombre").value(cliente1.getNombre()))
				.andExpect(jsonPath("$[1].nombre").value(cliente2.getNombre()));
	}

	@Test
	void testActualizarCliente() throws Exception {
		Cliente cliente = crearClienteValido();
		
		 when(clienteService.actualizarCliente(any(Cliente.class)))
	        .thenReturn(ResponseEntity.ok().build());
		
		mockMvc.perform(put("/clientes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cliente))).andExpect(status().isOk());
	}

	@Test
	void testEliminarCliente() throws Exception {
		Cliente cliente = crearClienteValido();
		
	    when(clienteService.eliminarCliente(any(Cliente.class)))
	        .thenReturn(ResponseEntity.ok().build());

	    mockMvc.perform(delete("/clientes")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(cliente)))
	        .andExpect(status().isOk());
	    
	}

	private Cliente crearClienteValido() {
		Cliente cliente = new Cliente();
		cliente.setId(2L);
		cliente.setEdad(25);
		cliente.setNombre("Juan Perez");
		cliente.setIdentificacion("1234567987");
		cliente.setGenero(Genero.M);
		cliente.setEstado(Estado.TRUE);
		cliente.setContraseña("1234$");
		return cliente;
	}
	
	private Cliente crearClienteInValido() {
		Cliente cliente = new Cliente();
		cliente.setId(2L);
		cliente.setEdad(15);
		cliente.setNombre("Juan Perez");
		cliente.setIdentificacion("1234567987");
		cliente.setGenero(Genero.M);
		cliente.setEstado(Estado.TRUE);
		cliente.setContraseña("1234$");
		return cliente;
	}
}
