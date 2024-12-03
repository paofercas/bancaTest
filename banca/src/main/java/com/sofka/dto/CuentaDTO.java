package com.sofka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sofka.entity.Estado;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentaDTO {
	
	private String numeroCuenta;
    private String tipo;
    private double saldoInicial;
    private Estado estado;
    private String cliente;
    private Long clienteId;
    private String clienteIdentificacion;

}
