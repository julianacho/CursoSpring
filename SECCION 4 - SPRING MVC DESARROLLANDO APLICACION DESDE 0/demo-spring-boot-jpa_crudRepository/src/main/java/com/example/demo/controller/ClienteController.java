package com.example.demo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entitys.Cliente;
import com.example.demo.models.service.IClienteService;

@Controller
@SessionAttributes("cliente") // guarda en sesion la informacion del cliente
public class ClienteController {
	
	@Autowired // Permite inyectarlo como un componente ya qu eel clienteService  tambien se agrego la anotacion	
	private IClienteService clienteService;
	
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {		
		Cliente cl=new Cliente();
		model.put("cliente", cl);
		model.put("titulo", "Formulario de Cliente");
		return "form";
		
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)//BindiBindingResult
	public String guardar(@Valid Cliente cl, BindingResult result, Model model , SessionStatus status ) { // SessionStatus permite revisar los atributos de sesion
		// @Valid permite validar la entidad de acuerdo a las anotaciones indicadas y su resultado lo da el objeto result 	
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form"; 
		}
		clienteService.save(cl);
		status.setComplete();// elimina los atributos de sesion
		return "redirect:listar";
		
	}
	
	@RequestMapping(value="/form/{id}") // Recibe por parametro el id del usuario y se la pasa al path de variable
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) { 
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")  // Recibe por parametro el id del usuario y se la pasa al path de variable
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			clienteService.delete(id);
		}
		return "redirect:/listar";
	}

}
