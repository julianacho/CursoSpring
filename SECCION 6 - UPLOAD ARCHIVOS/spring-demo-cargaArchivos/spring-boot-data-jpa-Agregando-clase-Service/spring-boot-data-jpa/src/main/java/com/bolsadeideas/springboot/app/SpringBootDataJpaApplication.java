package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.app.models.service.IUploadFileService;

// CommandLineRunner: Permite implementar metodo de run para que ejecuta acciones antes de iniciar el alicativo CommandLineRunner Uploads
@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {
	
	@Autowired
	IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		uploadFileService.deleteAll();
		uploadFileService.init();
		
	}
}
