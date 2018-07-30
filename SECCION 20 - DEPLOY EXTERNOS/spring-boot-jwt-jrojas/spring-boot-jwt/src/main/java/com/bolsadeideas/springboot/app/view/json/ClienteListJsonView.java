package com.bolsadeideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Component("listar.json") // Genera el beans y el acceso es -json por medio de la vista, es decir es una vista
// MappingJackson2JsonView permite deserializar el json
@SuppressWarnings("unchecked")
public class ClienteListJsonView extends MappingJackson2JsonView {
	
	@Override
	protected Object filterModel(Map<String, Object> model) {

		// Remove lo que no aplica para el json
		model.remove("titulo");
		model.remove("page");
		// Optiene el cliente
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		// Remuieve el cliente
		model.remove("clientes");
		// Modifica el cliente para que envie solo los atributos de la entidad
		model.put("clientes", clientes.getContent());
		
		return super.filterModel(model);
	}

}
