package com.sofka.dto;

import lombok.Data;

@Data
public class ClienteEvent {
	private Long clienteId;
    private String nombre;
    private String identificacion;
    private String evento;

}
