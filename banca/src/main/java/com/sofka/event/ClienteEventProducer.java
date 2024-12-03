package com.sofka.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;

@Service
public class ClienteEventProducer {
	
	private static final String TOPICO = "solicitar-cliente-id";

	@Autowired
	private KafkaTemplate<String, CuentaDTO> kafkaTemplate;

    public void solicitarClienteporNombre(CuentaDTO cuentaDTO) {
        kafkaTemplate.send(TOPICO, cuentaDTO);
        System.out.println("Mensaje enviado al tópico " + TOPICO + " con nombre: " + cuentaDTO.getCliente());
    }
    
}
