package com.sofka.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;

@Service
public class ClienteEventProducer {

	private final KafkaTemplate<String, CuentaDTO> kafkaTemplate;
	private final String topic = "clientes-topic";

    public ClienteEventProducer(KafkaTemplate<String, CuentaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarCliente(CuentaDTO cliente) {
        kafkaTemplate.send(topic, cliente.getClienteId().toString(), cliente);
    }
}
