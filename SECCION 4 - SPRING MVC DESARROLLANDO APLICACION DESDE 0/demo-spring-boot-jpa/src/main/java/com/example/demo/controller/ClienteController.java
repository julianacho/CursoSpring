package com.example.demo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entitys.Cliente;

@Controller
public class ClienteController {
	
	@Autowired // Permite inyectarlo como un componente ya qu eel cliente dao tambien se agrego la anotacion
	@Qualifier("ClienteDaoJPA") // Permite indicar cual implementacion se debe tomar
	private IClienteDao clienteDao;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteDao.findAll());
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
	public String guardar(@Valid Cliente cl, BindingResult result, Model model ) {
		// @Valid permite validar la entidad de acuerdo a las anotaciones indicadas y su resultado lo da el objeto result 	
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form"; 
		}
		clienteDao.save(cl);
		return "redirect:listar";
		
	}

}
