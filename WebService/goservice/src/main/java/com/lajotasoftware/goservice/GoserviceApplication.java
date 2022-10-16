package com.lajotasoftware.goservice;

//import casodeuso.UsuarioUC;
import com.lajotasoftware.goservice.Services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = { "com.lajotasoftware.goservice.Entity" })
@EnableJpaRepositories(basePackages = {	"com.lajotsoftware.goservice.DAO"})
@RestController
@EnableScheduling
@EnableAsync
public class GoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoserviceApplication.class, args);
	}

}