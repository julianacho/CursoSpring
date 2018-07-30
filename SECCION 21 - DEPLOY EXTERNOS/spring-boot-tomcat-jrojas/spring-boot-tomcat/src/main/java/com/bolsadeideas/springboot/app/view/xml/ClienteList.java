package com.bolsadeideas.springboot.app.view.xml;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
// clase wraper que envuelve los datos para ser convertido a xml
// @XmlRootElement para que estableca que esta es la clase root y se le indica el nombre de la etiqueta
@XmlRootElement(name="clientes")
public class ClienteList {
	
	// Se indica el nombre de la etiqueta que estara dentro de la etiqueta  clientes
	@XmlElement(name="cliente")
	public List<Cliente> clientes;

	public ClienteList() {}


	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

}
