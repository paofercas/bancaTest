package com.sofka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;
import com.sofka.dto.ErrorDto;
import com.sofka.entity.Cuenta;
import com.sofka.repository.CuentaRepository;

@Service
public class CuentaService {
	
	@Autowired
	private final CuentaRepository cuentaRepository;
	
	public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }
	
	public ResponseEntity<?> crearCuenta(Cuenta cuenta) {
        try {
        	return ResponseEntity.ok(cuentaRepository.save(cuenta));
        }catch (Exception ex) {
        	return new ResponseEntity<>(new ErrorDto("500", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> actualizarCuenta(Cuenta cuenta) {
    	 Optional<Cuenta> cuentaExistente = buscarPorId(cuenta.getNumeroCuenta());
         if (cuentaExistente.isEmpty()) {
             return ResponseEntity.notFound().build();
         }

         Cuenta cuentaActualizado = cuentaExistente.get();
         
         cuentaActualizado.setEstado(cuenta.getEstado());
         cuentaActualizado.setTipo(cuenta.getTipo());
         cuentaActualizado.setSaldoInicial(cuenta.getSaldoInicial());
         
         return ResponseEntity.ok(cuentaRepository.save(cuenta));
    }

    public Optional<Cuenta> buscarPorId(String id) {
        return cuentaRepository.findById(id);
    }
    
    public Cuenta buscarPorNumeroCuenta(String numCuenta) {
        return cuentaRepository.findByNumeroCuenta(numCuenta);
    }
    
    public List<Cuenta> buscarPorClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public ResponseEntity<?> eliminarCuenta(Cuenta cuenta) {
    	Optional<Cuenta> cuentaExistente = buscarPorId(cuenta.getNumeroCuenta());
        if (cuentaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cuenta cuentaEliminar = cuentaExistente.get();
        cuentaRepository.delete(cuentaEliminar);
        return ResponseEntity.ok().build(); 
    }
    
    public void crearCuentaParaCliente(CuentaDTO cuentaDTO) {
        System.out.println("Creando cuenta para el cliente: " + cuentaDTO.getClienteId() + " - " + cuentaDTO.getCliente());
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipo(cuentaDTO.getTipo()); 
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());
        cuenta.setClienteId(cuentaDTO.getClienteId());

        cuentaRepository.save(cuenta);
    }
    
}
