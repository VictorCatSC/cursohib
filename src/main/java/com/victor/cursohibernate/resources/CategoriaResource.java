package com.victor.cursohibernate.resources;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.service.CategoriaService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource
{
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	//@GetMapping
	public ResponseEntity<?> find(@PathVariable Integer id)
	{
		Categoria obj = service.buscar(id);

		return ResponseEntity.ok().body(obj);
	}
}
