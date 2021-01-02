package com.victor.cursohibernate;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.domain.Produto;
import com.victor.cursohibernate.repositoriesDAO.CategoriaRepository;
import com.victor.cursohibernate.repositoriesDAO.ProdutoRepository;
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

	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(CursohibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "computador", 2000.0);
		Produto p2 = new Produto(null, "impressora", 800.0);
		Produto p3 = new Produto(null, "mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}
}
