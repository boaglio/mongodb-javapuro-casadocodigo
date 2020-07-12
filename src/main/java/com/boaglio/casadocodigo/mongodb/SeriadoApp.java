package com.boaglio.casadocodigo.mongodb;

import java.util.Arrays;
import java.util.List;

public class SeriadoApp {

	public static void main(String[] args) {

		SeriadosRepository repositorio = new SeriadosRepository();

		List<Seriado> seriados = repositorio.findAll();

		// lista
		System.out.println("Total de seriados: "+seriados.size());

		// remove
		repositorio.remove(10);
		
		// novo 
		Seriado seriado3porCentro = new Seriado();
		seriado3porCentro.setId(10);
		seriado3porCentro.setNome("3%");
		List<String> personagens = Arrays.asList("Michele", "Joana", "Rafael","Marco","Ezequiel","Fernando");
		seriado3porCentro.setPersonagens(personagens);
		
		repositorio.insert(seriado3porCentro);
		
		// atualizando
		seriado3porCentro.setNome("3% - 3 por cento");
		repositorio.update(seriado3porCentro);

		// nova lista
		seriados = repositorio.findAll();
		System.out.println("Total de seriados: "+seriados.size());

	}

}
