package com.victor.cursohibernate.resources;

import com.victor.cursohibernate.domain.Categoria;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource
{
	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping
	public List<Categoria> listar(){
		Categoria cat1 = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");

		List<Categoria> categoriaList = new ArrayList<>();
		categoriaList.add(cat1);
		categoriaList.add(cat2);

		return categoriaList;
	}
}
