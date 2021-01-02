package com.victor.cursohibernate.resources;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource
{
	@Autowired
	private CategoriaService service;

	//@GetMapping
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id)
	{
		Categoria obj = service.buscar(id);

		return ResponseEntity.ok().body(obj);
	}
}
