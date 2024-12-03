package com.sofka.dto;

import java.math.BigDecimal;

import com.sofka.entity.Estado;

import lombok.Data;

@Data
public class CuentaDTO {
	
	private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Estado estado;
    private String cliente;
    private Long clienteId;
    private String clienteIdentificacion;

}
