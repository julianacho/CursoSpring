package com.example.demo.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.entitys.Cliente;

@Service // Permite que funcione como un unico metodo de acceso a los dao, es decir es la fachada de todos los servicios
public class CLienteServiceImpl implements IClienteService {
	
	@Autowired // Permite inyectar el cliente dao
	@Qualifier("ClienteDaoJPA") // Permite indicar cual implementacion se debe tomar
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly=true)// Solo es de consulta
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return clienteDao.findAll();
	}

	@Override
	@Transactional// Para insertar un nuevo registro
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly=true)// Solo es de consulta el servicio
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional// Para insertar un nuevo registro en el servicio
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.delete(id);
		
	}
	
  

}
