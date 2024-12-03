package com.sofka.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sofka.dto.ErrorDto;
import com.sofka.dto.ResponseDto;
import com.sofka.entity.Cuenta;
import com.sofka.entity.Movimientos;
import com.sofka.repository.MovimientosRepository;

@Service
public class MovimientosService {
	
	@Autowired
	private final MovimientosRepository movimientosRepository;
	
	@Autowired
	private CuentaService cuentaService;
	
	public MovimientosService(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }
	
	public ResponseEntity<?> crearMovimiento(Movimientos movimiento) {
		try {
		    Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
		    
		    if (cuenta == null || cuenta.getNumeroCuenta() == null) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                             .body(new ErrorDto("400", "La cuenta no existe"));
		    }

		    consultarSaldoDisponible(cuenta, movimiento.getValor());
		    
		    double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
		    movimiento.setSaldo(nuevoSaldo);
		    cuenta.setSaldoInicial(nuevoSaldo);
		    
		    cuentaService.actualizarCuenta(cuenta);
		    movimiento.setCuenta(cuenta);
		    Movimientos movimientoGuardado = movimientosRepository.save(movimiento);
		    
	        ResponseDto<Movimientos> response = new ResponseDto<>(movimientoGuardado);

	        return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ErrorDto error = new ErrorDto("500", ex.getMessage());
	        ResponseDto<?> errorResponse = new ResponseDto<>(error);

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
        
    }
	
	public ResponseEntity<?> consultarSaldoDisponible(Cuenta cuenta, double valor) {
	    // Verifica si el valor es negativo y su valor absoluto supera el saldo disponible
	    if (valor < 0 && cuenta.getSaldoInicial() < Math.abs(valor)) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDto("400", "Saldo no disponible"));
	    }
	    return ResponseEntity.ok("Saldo disponible");
	}
    
    public ResponseEntity<?> actualizarMovimiento(Movimientos movimiento) {
    	 Optional<Movimientos> movimientoExistente = buscarPorId(movimiento.getId());
         if (movimientoExistente.isEmpty()) {
             return ResponseEntity.notFound().build();
         }

         Movimientos movimientoActualizado = movimientoExistente.get();
         
         movimientoActualizado.setCuenta(movimiento.getCuenta());
         movimientoActualizado.setFecha(movimiento.getFecha());
         movimientoActualizado.setSaldo(movimiento.getSaldo());
         movimientoActualizado.setTipo(movimiento.getTipo());
         movimientoActualizado.setValor(movimiento.getValor());
         
         return ResponseEntity.ok(movimientosRepository.save(movimiento));
    }

    public Optional<Movimientos> buscarPorId(Long id) {
        return movimientosRepository.findById(id);
    }

    public List<Movimientos> listarMovimientos() {
        return movimientosRepository.findAll();
    }
    
    public List<Movimientos> findByFechaBetweenAndCuentaClienteId(LocalDateTime fechaInicio, LocalDateTime fechaFin, Long clienteId){
    	return movimientosRepository.findByFechaBetweenAndCuentaClienteId(fechaInicio, fechaFin, clienteId);
    }

    public ResponseEntity<?> eliminarMovimiento(Movimientos movimiento) {
    	Optional<Movimientos> movimientoExistente = buscarPorId(movimiento.getId());
        if (movimientoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Movimientos movimientoEliminar = movimientoExistente.get();
        movimientosRepository.delete(movimientoEliminar);
        return ResponseEntity.ok().build(); 
    }


}
