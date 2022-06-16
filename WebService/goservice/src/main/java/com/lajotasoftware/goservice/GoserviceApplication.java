package com.lajotasoftware.goservice;

//import casodeuso.UsuarioUC;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = {
		"com.lajotasoftware.goservice.Entity"
})
@EnableJpaRepositories(basePackages = {
		"com.lajotsoftware.goservice.DAO"
})
@RestController
public class GoserviceApplication {



/*	public void salvar(Object O){
		UsuarioUC uc = new UsuarioUC();
		uc.salvar((JSONPObject) O);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(GoserviceApplication.class, args);
	}

	/*@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		String query = "select * from usuario";
		return String.format(query);
		*//*return String.format("Hello %s!", name);*//*
		*//*return String.format("<!DOCTYPE html>\n" +
				"<html lang=\"en\">\n" +
				"<head>\n" +
				"    <meta charset=\"UTF-8\">\n" +
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"    <title>Document</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"<h1>bom dia</h1>\n" +
				"</body>\n" +
				"</html>");*//*
	}*/
}