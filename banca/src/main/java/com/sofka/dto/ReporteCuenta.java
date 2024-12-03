package com.sofka.dto;

import java.util.List;

import com.sofka.entity.Cuenta;
import com.sofka.entity.Movimientos;

import lombok.Data;

@Data
public class ReporteCuenta {

	private Cuenta cuenta;
	private List<Movimientos> movimientos;
	
	public ReporteCuenta(Cuenta cuenta, List<Movimientos> movimientos) {
		super();
		this.cuenta = cuenta;
		this.movimientos = movimientos;
	}
	
}
