package com.bolsadeideas.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;


@RestController // Permite que sea una clase de controlador solo con respuestas rest es decir json o xml
@RequestMapping("/api/clientes") // Ruta de acceso al controlador REST
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/listar-xml")
	public ClienteList listarXml() {
		return new ClienteList(clienteService.findAll());
	}
	
	@GetMapping(value = "/listar")
	public List<Cliente> listar() {
		return clienteService.findAll();
	}
	

}
