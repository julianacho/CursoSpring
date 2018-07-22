package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // Determina que es una clase de configuracion
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	// Genera el log del log
	private final Logger log= org.slf4j.LoggerFactory.getLogger(getClass());

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		// TODO Auto-generated method stub
//		super.addResourceHandlers(registry);
//		String resourcePath = Paths.get("Uploads").toAbsolutePath().toUri().toString();		
//		log.info("resourcePath: "+resourcePath);
//		
//		
//		
//		registry.addResourceHandler("/Uploads/**")
//		.addResourceLocations(resourcePath);
//	}

}
