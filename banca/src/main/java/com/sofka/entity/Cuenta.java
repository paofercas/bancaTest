package com.sofka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name="cuenta")
public class Cuenta {

	@Id
	private String numeroCuenta;
	
	@NotNull(message = "{cuenta.clienteid.notnull}")
    private Long clienteId; 
	
	@NotNull(message = "{cuenta.tipo.notnull}")
    @NotEmpty(message = "{cuenta.tipo.notempty}")
	private String tipo;
	
	@NotNull(message = "{cuenta.saldoInicial.notnull}")
	@Positive
	private double saldoInicial;
	
	@NotNull(message = "{cuenta.estado.notnull}")
	@Enumerated(EnumType.STRING)
    private Estado estado;
}
