package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.service.IClienteService;


@Controller
@RequestMapping("/factura")// Se establece la ruta base del controlador
@SessionAttributes("factura") // Para mantener la factura en la sesion
public class FacturaController {
	
	@Autowired // Inyecta el cliente service debido a que es un componente
	private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}") // Establece la ruta de acceso en donde seria /factura/form/{clienteId}
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar"; // Redirige a listar del controlador factura
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.put("factura", factura);
		model.put("titulo", "Crear Factura");

		return "factura/form"; // Retorna a la vista de factura/form
	}

}
