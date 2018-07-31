package com.jgr.springboot.backend.apirest.models.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jgr.springboot.backend.apirest.models.entity.Cliente;


public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findById(Long id);
	
	public void delete(Cliente cliente);

}
