package com.victor.cursohibernate.services;

import com.victor.cursohibernate.domain.Categoria;
import com.victor.cursohibernate.repositoriesDAO.CategoriaRepository;
import com.victor.cursohibernate.services.exceptions.DataIntegrityException;
import com.victor.cursohibernate.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService
{
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id)
	{
		Optional<Categoria> obj = repo.findById(id);

		//Se obj == null lanca exception
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj)
	{
		obj.setId(null);//certificar q é uma insercao e nao alteracao
		return repo.save(obj);
	}

	public Categoria update(Categoria obj)
	{
		find(obj.getId());

		return repo.save(obj);
	}

	public void delete(Integer categoryId)
	{
		find(categoryId);
		try
		{
			repo.deleteById(categoryId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll()
	{
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}
