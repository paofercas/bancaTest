package com.sofka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sofka.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
	
	@Query(value = "SELECT * FROM cuenta where numeroCuenta= :numCuenta AND estado = 'TRUE'", nativeQuery = true)
	Cuenta findByNumeroCuenta(@Param("numCuenta") String numCuenta);
	
	List<Cuenta> findByClienteId(Long clienteId);

}
