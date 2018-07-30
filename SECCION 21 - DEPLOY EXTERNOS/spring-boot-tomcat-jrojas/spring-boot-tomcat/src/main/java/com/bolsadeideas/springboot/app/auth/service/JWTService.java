package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

/**
 * Establece el comportamiento de las clases a crear
 * @author julian
 *
 */
public interface JWTService {
	
	public String create(Authentication auth) throws IOException;
	public boolean validate(String token);
	public Claims getClaims(String token);
	public String getUsername(String token);
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
	public String resolve(String token);

}
