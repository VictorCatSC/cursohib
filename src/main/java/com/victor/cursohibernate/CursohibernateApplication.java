package com.victor.cursohibernate;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.repositoriesDAO.CategoriaRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursohibernateApplication implements CommandLineRunner
{

	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(CursohibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
