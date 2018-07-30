package com.bolsadeideas.springboot.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase MIX para enviar el rol
 * @author julian
 *
 */
public abstract class SimpleGrantedAuthorityMixin {
	// JsonCreator, crea a partir de un json
	// @JsonProperty("authority") que atributo se inyecta en el constructor
	// El atributo authority posee el rol y lo envia
	@JsonCreator
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {}

}
