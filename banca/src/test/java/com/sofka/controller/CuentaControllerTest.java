package com.sofka.controller;

import com.sofka.dto.CuentaDTO;
import com.sofka.entity.Cuenta;
import com.sofka.entity.Estado;
import com.sofka.service.CuentaService;
import com.sofka.event.ClienteEventProducer;
import com.sofka.event.ClienteEventConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @Mock
    private ClienteEventProducer clienteEventProducer;

    @Mock
    private ClienteEventConsumer clienteEventConsumer;

    @InjectMocks
    private CuentaController cuentaController;

    private CuentaDTO cuentaDTO;
    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuentaDTO = new CuentaDTO();
        cuentaDTO.setClienteId(1L);
        cuentaDTO.setCliente("Juan Pérez");
        cuentaDTO.setClienteIdentificacion("123456");

        cuenta = new Cuenta();
        cuenta.setClienteId(1L);
        cuenta.setNumeroCuenta("34343434");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(Estado.TRUE);
        cuenta.setTipo("Ahorros");
    }

    @Test
    void testCrearCuenta() {
    	
    	doNothing().when(clienteEventProducer).solicitarClienteporNombre(cuentaDTO);
        ResponseEntity<?> response = cuentaController.crearCuenta(cuentaDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Solicitud de creación de cuenta en proceso.", response.getBody());
        verify(clienteEventProducer, times(1)).solicitarClienteporNombre(cuentaDTO);
    }

    @Test
    void testListarCuentas() {
        
    	List<Cuenta> cuentas = Arrays.asList(cuenta);
        when(cuentaService.listarCuentas()).thenReturn(cuentas);
        List<Cuenta> response = cuentaController.listarCuentas();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(cuenta, response.get(0));
        verify(cuentaService, times(1)).listarCuentas();
    }

    @Test
    void testActualizarCuenta() {
        
    	when(cuentaService.actualizarCuenta(cuenta)).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> response = cuentaController.actualizarCuenta(cuenta);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEliminarCuenta() {
        
        when(cuentaService.eliminarCuenta(cuenta)).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> response = cuentaController.eliminarCuenta(cuenta);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cuentaService, times(1)).eliminarCuenta(cuenta);
    }
}
