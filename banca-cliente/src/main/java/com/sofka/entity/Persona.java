package com.sofka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED) 
@Table(name="persona")
public class Persona {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "{cliente.identificacion.notnull}")
    @NotEmpty(message = "{cliente.identificacion.notempty}")
	@Column(unique= true)
	private String identificacion;
	
	@NotNull(message = "{cliente.nombre.notnull}")
    @NotEmpty(message = "{cliente.nombre.notempty}")
	private String nombre;

	@NotNull(message = "{cliente.estado.notnull}")
	@Enumerated(EnumType.STRING)
    private Genero genero;
	
	@NotNull(message = "{cliente.edad.notnull}")
	@Min(value = 18, message = "{cliente.edad.min}")
    @Max(value = 100, message = "{cliente.edad.max}")
	private int edad;
	
	private String direccion;

	private String telefono;

}
