package com.example.demo.models.dao;

import java.util.List;

import com.example.demo.models.entitys.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);

}
