package com.victor.cursohibernate.services;

import com.victor.cursohibernate.DTO.ClienteDTO;
import com.victor.cursohibernate.domain.Cliente;
import com.victor.cursohibernate.repositoriesDAO.ClienteRepository;
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
public class ClienteService
{
	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id)
	{
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente cliente)
	{
		Cliente newCliente = buscar(cliente.getId());
		updateData(newCliente, cliente);
		return repo.save(newCliente);
	}

	private void updateData(Cliente newCliente, Cliente cliente)
	{
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public void delete(Integer categoryId)
	{
		buscar(categoryId);
		try
		{
			repo.deleteById(categoryId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

	public List<Cliente> findAll()
	{
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente catFromDTO(ClienteDTO categoriaDTO){
		return new Cliente(categoriaDTO.getId(), categoriaDTO.getName(), categoriaDTO.getEmail(), null, null);
	}


}
