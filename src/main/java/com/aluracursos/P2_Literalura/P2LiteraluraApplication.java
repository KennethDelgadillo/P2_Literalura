package com.aluracursos.P2_Literalura;

import com.aluracursos.P2_Literalura.principal.Principal;
import com.aluracursos.P2_Literalura.repository.AutorRepository;
import com.aluracursos.P2_Literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class P2LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(P2LiteraluraApplication.class, args);
	}

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository autorRepository;
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository,autorRepository);
		principal.MuestraElMenu();
	}
}
