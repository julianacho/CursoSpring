package com.bolsadeideas.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Component("listar.xml") // Se registra como un componente, para que se pueda resolver
// extiende de MarshallingView, con el fin de que renderice cuando serializa
public class ClienteListXmlView  extends MarshallingView {
	
	// Consutructor de la inyeccion
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Modificamos el objeto modelo con el request
		model.remove("titulo");
		model.remove("page");
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");		
		model.remove("clientes");	
		// Hace la implementacion del objeto clientes sobre ClienteList
		model.put("clienteList", new ClienteList(clientes.getContent()));
		// Llama a la super vista para que continue con la asignacion de la serializacion		
		super.renderMergedOutputModel(model, request, response);
	}

}
