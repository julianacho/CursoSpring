package com.jgr.springboot.backend.apirest.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jgr.springboot.backend.apirest.models.entity.Cliente;
import com.jgr.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = { "http://localhost:4200" }) // Indica que solo puede acceder desde esta url
@RestController // IMPLEMENTA COMO UN API RES
@RequestMapping("/api") // URL DEL END
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;

	// Tipo get
	@GetMapping("/clientes") // Mapeo de la url de tipo Get
	@ResponseStatus(HttpStatus.OK)// Responde el estado de correcto
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")// Se envi el id por parametro @PathVariable
	@ResponseStatus(HttpStatus.OK)// Responde el estado de correcto aunque este se asigna por defecto
	public Cliente show(@PathVariable Long id) {
		return this.clienteService.findById(id);
	}

	// Tipo post recibe el objeto cliente por parametro
	@PostMapping("/clientes")// los datos bienen dentro de los datos de la peticion
	@ResponseStatus(HttpStatus.CREATED) // Responde el estatus de creado
	public Cliente create(@RequestBody Cliente cliente) {
		cliente.setCreateAt(new Date());
		this.clienteService.save(cliente);
		return cliente;
	}

	// Tipo Put la peticion, recibe el cliente que esta dentro del cuerpo del request
	// El id que se envia como variable
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)	
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente currentCliente = this.clienteService.findById(id);
		currentCliente.setNombre(cliente.getNombre());
		currentCliente.setApellido(cliente.getApellido());
		currentCliente.setEmail(cliente.getEmail());
		this.clienteService.save(currentCliente);
		return currentCliente;
	}

	// Tipo delete
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Cliente currentCliente = this.clienteService.findById(id);
		this.clienteService.delete(currentCliente);
	}

}
