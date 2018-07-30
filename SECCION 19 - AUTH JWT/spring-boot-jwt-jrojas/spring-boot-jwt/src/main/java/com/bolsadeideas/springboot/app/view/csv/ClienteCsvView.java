package com.bolsadeideas.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

// Se marca como compnetnte para que se asocie como un bean
// el metodo de acceso listar, como es una sola clase no es necesario indicar la extension
// AbstractView se utiliza una vista mas abstracta
@Component("listar.csv")
public class ClienteCsvView extends AbstractView {
	
	/**
	 * indica el tipo de archivo a generar
	 */
	public ClienteCsvView() {
		// indica el tipo de archivo a generar
		setContentType("text/csv");
	}

	/**
	 * Hacemos que genere un contenido descargable
	 */
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Indica el nombre del archivo a generar
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		// Establece el tipo de archivo
		response.setContentType(getContentType());

		// Optiene el cliente
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		// Optiene un escritor tipo CSV, apartir del escritor del response 
		// Establece que se guadara el archivo plano en la response
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),  CsvPreference.STANDARD_PREFERENCE);
		// Genera arreglo de las cabeceras del CSV
		String[] header = {"id", "nombre", "apellido", "email", "createAt"};
		// Escribe la cabecera
		beanWriter.writeHeader(header);
		
		// Escribe cada uno de los clientes que pertenezcan al header
		for(Cliente cliente: clientes) {
			// Combietre los atributos getter en una linea del archivo plano
			beanWriter.write(cliente, header);			
		}
		
		beanWriter.close();
	}

}

