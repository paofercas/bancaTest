package com.sofka.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sofka.dto.CuentaDTO;
import com.sofka.entity.Cliente;
import com.sofka.entity.Estado;
import com.sofka.entity.Genero;
import com.sofka.event.ClienteEventProducer;
import com.sofka.repository.ClienteRepository;


public class ClienteServiceTest {

	@InjectMocks
	private ClienteService clienteService;

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ClienteEventProducer clienteEventProducer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
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
	
	@Test
	void testCrearCliente() {
		Cliente cliente = crearClienteValido();

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        doNothing().when(clienteEventProducer).publicarCliente(any(CuentaDTO.class));

        ResponseEntity<?> response = clienteService.crearCliente(cliente);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); 
        assertEquals(cliente, response.getBody()); 
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteEventProducer, times(1)).publicarCliente(any(CuentaDTO.class));
	}

	@Test
	void testActualizarCliente() {
		Cliente cliente = crearClienteValido();
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
		doNothing().when(clienteEventProducer).publicarCliente(any(CuentaDTO.class));

		ResponseEntity<?> response = clienteService.actualizarCliente(cliente);

		assertNotNull(response);
		assertEquals(ResponseEntity.ok(cliente).getStatusCode(), response.getStatusCode());
		verify(clienteRepository, times(1)).findById(cliente.getId());
		verify(clienteRepository, times(1)).save(cliente);
		verify(clienteEventProducer, times(1)).publicarCliente(any(CuentaDTO.class));
	}

	@Test
	void testActualizarClienteNoExistente() {
		Cliente cliente = crearClienteValido();
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

		ResponseEntity<?> response = clienteService.actualizarCliente(cliente);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(clienteRepository, times(1)).findById(cliente.getId());
		verify(clienteRepository, never()).save(any(Cliente.class));
		verify(clienteEventProducer, never()).publicarCliente(any(CuentaDTO.class));
	}

	@Test
	void testEliminarCliente() {
		Cliente cliente = crearClienteValido();
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

		ResponseEntity<?> response = clienteService.eliminarCliente(cliente);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(clienteRepository, times(1)).findById(cliente.getId());
		verify(clienteRepository, times(1)).delete(cliente);
	}

	@Test
	void testEliminarClienteNoExistente() {
		Cliente cliente = crearClienteValido();
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

		ResponseEntity<?> response = clienteService.eliminarCliente(cliente);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(clienteRepository, times(1)).findById(cliente.getId());
		verify(clienteRepository, never()).delete(any(Cliente.class));
	}

	@Test
	void testListarClientes() {
		Cliente cliente = crearClienteValido();
		when(clienteRepository.findAll()).thenReturn(List.of(cliente));

		List<Cliente> clientes = clienteService.listarClientes();

		assertNotNull(clientes);
		assertEquals(1, clientes.size());
		assertEquals("Juan Perez", clientes.get(0).getNombre());
		verify(clienteRepository, times(1)).findAll();
	}

}
