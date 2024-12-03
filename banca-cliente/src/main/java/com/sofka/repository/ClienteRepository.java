package com.sofka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sofka.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query(value = "SELECT * FROM persona p INNER JOIN cliente c ON p.id = c.id WHERE p.nombre = :name AND c.estado = 'TRUE'", nativeQuery = true)
	Cliente findByName(@Param("name") String nombre);

}
