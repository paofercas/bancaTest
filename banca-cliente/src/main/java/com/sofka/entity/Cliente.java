package com.sofka.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name="cliente")
public class Cliente extends Persona {
			
	@NotNull(message = "{cliente.contrasena.notnull}")
    @NotEmpty(message = "{cliente.contrasena.notempty}")
	private String contraseña;
	
	@NotNull(message = "{cliente.estado.notnull}")
	@Enumerated(EnumType.STRING)
    private Estado estado;

}
