package com.lajotasoftware.goservice;

import casodeusotest.UsuarioUC;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoserviceApplicationTests {

	@Test
	void contextLoads() {
		UsuarioUC usuariouc = new UsuarioUC();
		usuariouc.criaUsuario();
	}

}
