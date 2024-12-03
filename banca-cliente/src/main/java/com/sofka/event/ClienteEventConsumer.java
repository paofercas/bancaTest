package com.sofka.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sofka.dto.CuentaDTO;
import com.sofka.entity.Cliente;
import com.sofka.service.ClienteService;

@Service
public class ClienteEventConsumer {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private KafkaTemplate<String, CuentaDTO> kafkaTemplate;

    public ClienteEventConsumer(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @KafkaListener(topics = "solicitar-cliente-id", groupId = "banca-group")
    public void procesarSolicitudClienteId(CuentaDTO cuentaDTO) {
    	 Cliente cliente= clienteService.buscarPorNombre(cuentaDTO.getCliente());
    	 Long clienteId = cliente != null ? cliente.getId() : null;
    	 cuentaDTO.setClienteId(clienteId);
    	 cuentaDTO.setClienteIdentificacion(cliente.getIdentificacion());
         kafkaTemplate.send("respuesta-cliente-id",cuentaDTO);
         System.out.println("Enviada respuesta para cliente:" + cuentaDTO.getCliente());
     }    
    
}
