package com.lajotasoftware.goservice;

//import casodeuso.UsuarioUC;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
@EntityScan(basePackages = { "com.lajotasoftware.goservice.Entity" })
@EnableJpaRepositories(basePackages = {	"com.lajotsoftware.goservice.DAO"})
@RestController

//spring.jpa.hibernate.ddl-auto=create-drop
public class GoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoserviceApplication.class, args);
	}

}