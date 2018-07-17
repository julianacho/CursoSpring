package com.example.demo.controollers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@Value("${application.controller.titulo}") // Inyecta el titulo a partir del aplicatiob.prppertis
	private String titulo;
	
	@GetMapping("/index") // Indica la ruta get de la peticion
	public String index(Model model) {
		model.addAttribute("titulo", this.titulo);
		return "index";
	}
	

}
