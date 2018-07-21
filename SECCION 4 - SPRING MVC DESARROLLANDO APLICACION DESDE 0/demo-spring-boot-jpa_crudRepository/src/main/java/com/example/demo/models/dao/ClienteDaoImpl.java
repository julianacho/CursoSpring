package com.example.demo.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.entitys.Cliente;

@Repository("ClienteDaoJPA") // Marca la clase como de persistencia de acceso a datos
public class ClienteDaoImpl implements IClienteDao {
	
	@PersistenceContext // De forma automatica iyecta la configuracion que se establezca
	private EntityManager em;

	 
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}

	
	@Override	
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub		
		if(cliente.getId() != null && cliente.getId() >0) {
			em.merge(cliente);
		} else {
			em.persist(cliente);
		}
		
	}


	@Override	
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Cliente.class, id);
	}


	@Override	
	public void delete(Long id) {
		em.remove(findOne(id));
		
	}

}
