package com.victor.cursohibernate.services;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.repositoriesDAO.CategoriaRepository;
import com.victor.cursohibernate.services.exceptions.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService
{
	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id)
	{
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}


}
