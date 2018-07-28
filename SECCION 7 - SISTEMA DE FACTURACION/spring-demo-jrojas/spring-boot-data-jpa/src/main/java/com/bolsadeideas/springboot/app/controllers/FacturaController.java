package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.IClienteService;


@Controller
@RequestMapping("/factura")// Se establece la ruta base del controlador
@SessionAttributes("factura") // Para mantener la factura en la sesion
public class FacturaController {
	
	@Autowired // Inyecta el cliente service debido a que es un componente
	private IClienteService clienteService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/ver/{id}") // Peteicion Get
	//@PathVariable permite pasar el id de la vista
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);

		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos!");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		return "factura/ver";
	}
	
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" }) // produces genera una salida json
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) { // @ResponseBody permite retirnar la respuesta no sobre una vista si no sobre la respuesta del body de la respuesta
		return clienteService.findByNombre(term);
	}
	
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
	
	@PostMapping("/form") // Tipo de peticion post por que guarda
	                      // @Valid permite habilitar la validacion de la factura 
	                      // Model Permite pasar datos a la vista	
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId, // Toma el valor del item id por arreglo por que son varios
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Error: La factura NO puede no tener líneas!");
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);

			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		clienteService.saveFactura(factura);
		status.setComplete(); // ELimina el objeto de sesion es decir la factura

		flash.addFlashAttribute("success", "Factura creada con éxito!");

		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/eliminar/{id}") // Metodo tipo get para eliminar 
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);
		if (factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito!");
			return "redirect:/ver/" + factura.getCliente().getId();
		}
		flash.addFlashAttribute("error", "La factura no existe en la base de datos, no se pudo eliminar!");

		return "redirect:/listar";
	}

}
