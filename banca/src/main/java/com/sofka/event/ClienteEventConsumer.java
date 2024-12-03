package com.sofka.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;
import com.sofka.service.CuentaService;

@Service
public class ClienteEventConsumer {
	
	private final CuentaService cuentaService;
	
	private Map<Long, CuentaDTO> clientesCache = new HashMap<>();

    public ClienteEventConsumer(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @KafkaListener(topics = "respuesta-cliente-id", groupId = "respuestas-group", concurrency = "1", containerFactory = "kafkaListenerContainerFactory")
    public void recibirClienteId(CuentaDTO cuentaDTO) {
      System.out.println("Recibido clienteId" + cuentaDTO.getClienteId()+ "para nombre:" + cuentaDTO.getCliente());
      cuentaService.crearCuentaParaCliente(cuentaDTO);
    }
    
    
    @KafkaListener(topics = "clientes-topic", groupId = "reportes-group")
    public void consumirCliente(CuentaDTO cuentaDTO) {
        clientesCache.put(cuentaDTO.getClienteId(), cuentaDTO);
    }

    public CuentaDTO obtenerCliente(Long clienteId) {
        return clientesCache.get(clienteId);
    }
    
}
