package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;

/**
 * Es el filtro de autorizacion, es decir verifica el token enviado
 * @author julian
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	/**
	 * Se ejecuta en cada request, es decir cada peticion
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Valida si inicia la variable Authorization si posee como inicial la palabra Bearer
		// Si es asi queire decir que necesita verificacion del token
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = null;
		// Metodo valida el token
		if(jwtService.validate(header)) {
			// Valida si tiene acceso a la peticion de acuerdo al rol del usuario
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
		}
		// Autentica al usuario dentro de la peticion
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Continua con la peticion normal
		chain.doFilter(request, response);
		
	}

	/**
	 * Permite verificar si requiere autenticacion
	 * @param header
	 * @return
	 */
	protected boolean requiresAuthentication(String header) {

		// Evalua si inicia con la palabra Beaberm indica que necesita autorizacion
		if (header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

}
