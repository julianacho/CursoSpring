package com.bolsadeideas.springboot.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;

//import java.nio.file.Paths;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		super.addResourceHandlers(registry);
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		log.info(resourcePath);
		
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations(resourcePath);
		
	}*/
	
	/**
	 * Permite adicionar controladores estaticos para que solo levanle la pagina
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	/**
	 * pERMITE HABILITAR EL USO DEL PASSWORD ENCONDER Y REGISTRALA EN EL CONTENEDOR PARA QUE PUEDA SER INYECTADA
	 * @return
	 */
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// INICIA EL LOCALE DEL LENGUAJE POR DEFECTO
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		return localeResolver;
	}
	// UN INTERCEPTOR QUE PERMITE CAMBIAR EL LENGUAJE , ES DECIR CAMBIA EL LOCALE SARVER
	// UBICA EL INTERCETOR CON EL NOMBRE DE lang Y ANALIZA SU VALOR
	@Bean	
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		return localeInterceptor;
	}

	// METODO PARA ADICIONAR UN INTERCEPTOR CREADO ANTERIORMENTE
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(localeChangeInterceptor());
	}

	
}
