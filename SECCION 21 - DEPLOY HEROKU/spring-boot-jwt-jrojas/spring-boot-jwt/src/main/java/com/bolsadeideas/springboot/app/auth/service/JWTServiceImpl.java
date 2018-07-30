package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component // Se establece como un componenct para que sea como un objeto de spring
public class JWTServiceImpl implements JWTService {
	
public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes());
	
	public static final long EXPIRATION_DATE = 14000000L; // milisegundos 
	public static final String TOKEN_PREFIX = "Bearer "; // es un prefijo del tiken
	public static final String HEADER_STRING = "Authorization";
	
	@Override
	public String create(Authentication auth) throws IOException {

		String username = ((User) auth.getPrincipal()).getUsername();

		// Se determinan los roles que posee el usuario autenticado
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		// Objeto de reclamaciones para registrar los roles
		Claims claims = Jwts.claims();
		// new ObjectMapper().writeValueAsString(roles): Pasa los roles a string con estructura json
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(username)
				.signWith(SignatureAlgorithm.HS512, // Algortimo de encriptacion
						SECRET.getBytes())// Clave Secreta
				.setIssuedAt(new Date())// Fecha de creacion del token
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))// Fecha de expiracion del token + Milisengudos de expiracion del token
				.compact();

		return token;
	}

	/**
	 * Metodo que realiza la validacion del token, en caso de haber un error en el token retorna el false
	 */
	@Override
	public boolean validate(String token) {

		try {

			getClaims(token);

			return true;
		} catch (JwtException | IllegalArgumentException e) {
			// Aca se puede tomar diferentes tipos de excepciones
			return false;
		}

	}

	/**
	 * Permite tomar el token y tomar los datos del token
	 * es decir valida la firma del token
	 */
	@Override
	public Claims getClaims(String token) {
		//claims son los datos del token
		Claims claims = Jwts.parser().
				setSigningKey(SECRET.getBytes())
				.parseClaimsJws(resolve(token)).
				getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		// TODO Auto-generated method stub
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		// Se para a un json de autorizaciones
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

		return authorities;
	}

	/**
	 * retorna solo el token, quieta el beaber
	 */
	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}
