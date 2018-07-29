package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

import org.springframework.security.core.userdetails.User;
// @EnableGlobalMethodSecurity permite autorizar el uso de anotaciones para la validacion en los metodos del controlador habilita tambien @PreAuthorize("hasRole('ROLE_USER')")
// para validar los accesos por metodos
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration // Es clase de configracion
// WebSecurityConfigurerAdapter
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Se inyecta el componente
	@Autowired
	private LoginSuccessHandler successHandler;
	
	// Se inyecta el data source, la conexion a la bd
	@Autowired 
	private DataSource dataSource;
	
	// Se inyecta el servciio de login
	@Autowired
	private JpaUserDetailsService userDetailsService;

	//Se inyecta el password encoder
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Permite la condiguracon de seguridad de las rutas de acceso
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		Se comentaria por que la autorizacion se puede hacer por anotaciones sobre los metodos

		http.authorizeRequests()
		.antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()   // Permite a todos acceder a esas rutas
		//.antMatchers("/ver/**").hasAnyRole("USER") // SOlo los usuarios con rol USER puede acceder a la ruta ver
		//.antMatchers("/uploads/**").hasAnyRole("USER")
		//.antMatchers("/form/**").hasAnyRole("ADMIN")  // Solo el admino puede aaceder a estas rutas
		//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		//.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated() // Ejecuta el form de autenticacion que trae spring
		.and()
		    .formLogin()
		        .successHandler(successHandler) // Inyecta el successHandler que permite personalizar las acciones del logueo
		        .loginPage("/login") // Indica que la pagina de login es /login
		    .permitAll()
		.and()
		.logout().permitAll()
		.and() // Adiciona la pagina de error cuando hay error de acceso
		.exceptionHandling().accessDeniedPage("/error_403");

	}

	/**
	 * Permite crear los usuarios en memoria por defecto
	 * @param build
	 * @throws Exception
	 */
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		/*
		 * Deprecated
		 * UserBuilder users = User.withDefaultPasswordEncoder();
		 * */
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//1. forma Crea un usuaro por defecto en memoria
//		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//		// Creamos los posibles usuario con los que probaremos
//		build.inMemoryAuthentication()
//		.withUser(users.username("admin").password("admin").roles("ADMIN", "USER"))
//		.withUser(users.username("desjgr").password("desjgr").roles("USER"));
		
		// Configura la forma de hacer la autenticacion, es decir la consulta para autenticacion  y revision de roles
		
		// 2. FORMA POR MEDIO DE CONSULTA NATIVA
//		build.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder)
//		.usersByUsernameQuery("select username, password, enabled from users where username=?")
//		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
		
		// 3. FORMA por medio del servicio
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}

}
