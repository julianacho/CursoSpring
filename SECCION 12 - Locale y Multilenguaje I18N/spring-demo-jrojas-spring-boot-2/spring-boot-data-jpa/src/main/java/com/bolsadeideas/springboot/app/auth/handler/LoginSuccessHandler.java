package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	// EN el momento que se autentique se debe hacer lo siguiente
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// Toma los mensajes de flash
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		// Se crea un map de flash mensajes
		FlashMap flashMap = new FlashMap();		
		flashMap.put("success", "Hola " +authentication.getName()+ ", haz iniciado sesión con éxito!");
		// Se guarda en el manager del flash
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		// Objeto autentication posee el nombre de usuario
		if(authentication != null) {
			logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
