package com.bolsadeideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	// Se genera el mensaje en propertis de manera no estatica
	@Value("${application.controllers.mensaje}")
	private String mensaje;
	
	@GetMapping("/") // Genera para que sea el rut del sitio
	public String hola(Model model) {
		model.addAttribute("mensaje", mensaje);
		return "hola";
	}
}
