package com.sofka.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="movimientos")
public class Movimientos {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "cuentaId", referencedColumnName = "numeroCuenta")
	@NotNull(message = "{movimientos.cuenta.notnull}")
    private Cuenta cuenta;
	
	@NotNull(message = "{movimientos.fecha.notnull}")
	private LocalDateTime  fecha;
	
	@NotNull(message = "{movimientos.tipo.notnull}")
    @NotEmpty(message = "{movimientos.tipo.notempty}")
	private String tipo;
	
	@NotNull(message = "{movimientos.valor.notnull}")
    private double valor;
	
	private double saldo;
	
}
