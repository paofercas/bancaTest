package com.sofka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;
import com.sofka.entity.Cliente;
import com.sofka.event.ClienteEventProducer;
import com.sofka.exception.GlobalExceptionHandler.ErrorResponse;
import com.sofka.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private final ClienteRepository clienteRepository;

	@Autowired
	private ClienteEventProducer clienteEventProducer;

	public ClienteService(ClienteRepository clienteRepository, ClienteEventProducer clienteEventProducer) {
		this.clienteRepository = clienteRepository;
		this.clienteEventProducer= clienteEventProducer;
	}

	public ResponseEntity<?> crearCliente(Cliente cliente) {

		try {
			CuentaDTO cuenta = new CuentaDTO();
			cuenta.setClienteId(cliente.getId());
			cuenta.setCliente(cliente.getNombre());
			cuenta.setClienteIdentificacion(cliente.getIdentificacion());
			clienteEventProducer.publicarCliente(cuenta);
			return ResponseEntity.ok(clienteRepository.save(cliente));
		} catch (Exception ex) {
			return new ResponseEntity<>(new ErrorResponse("500", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> actualizarCliente(Cliente cliente) {
		Optional<Cliente> clienteExistente = buscarPorId(cliente.getId());
		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cliente clienteActualizado = clienteExistente.get();

		clienteActualizado.setNombre(cliente.getNombre());
		clienteActualizado.setTelefono(cliente.getTelefono());
		clienteActualizado.setDireccion(cliente.getDireccion());
		clienteActualizado.setEdad(cliente.getEdad());
		clienteActualizado.setGenero(cliente.getGenero());

		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setClienteId(cliente.getId());
		cuenta.setCliente(cliente.getNombre());
		cuenta.setClienteIdentificacion(cliente.getIdentificacion());
		clienteEventProducer.publicarCliente(cuenta);

		return ResponseEntity.ok(clienteRepository.save(cliente));
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente buscarPorNombre(String nombre) {
		return clienteRepository.findByName(nombre);
	}

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public ResponseEntity<?> eliminarCliente(Cliente cliente) {
		Optional<Cliente> clienteExistente = buscarPorId(cliente.getId());
		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente clienteEliminar = clienteExistente.get();
		clienteRepository.delete(clienteEliminar);
		return ResponseEntity.ok().build();
	}

}
