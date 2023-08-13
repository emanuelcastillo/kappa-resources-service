package com.kappa.resources;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
//lo podemos obviar ya que viene implicito en SrpingBootAplication
public class SourceApplication implements CommandLineRunner /*con esta implementacion puedo correr algo cuando se inica la app*/ {

	public static void main(String[] args) {
		SpringApplication.run(SourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\n SERVIDOR DE RECURSOS DE KAPPA EN LINEA");
	}
}
