package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

//	public List<Cliente> findAll();
//
//	public Cliente save(Cliente cliente);
//	
//	public Cliente findOne(Long id);
//	
//	public void delete(Long id);

}
