package com.victor.cursohibernate.services;

import com.victor.cursohibernate.DTO.ClienteDTO;
import com.victor.cursohibernate.DTO.ClienteNewDTO;
import com.victor.cursohibernate.domain.Cidade;
import com.victor.cursohibernate.domain.Cliente;
import com.victor.cursohibernate.domain.Endereco;
import com.victor.cursohibernate.domain.enums.TipoCliente;
import com.victor.cursohibernate.repositoriesDAO.ClienteRepository;
import com.victor.cursohibernate.repositoriesDAO.EnderecoRepository;
import com.victor.cursohibernate.services.exceptions.DataIntegrityException;
import com.victor.cursohibernate.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService
{

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id)
	{
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente cliente)
	{
		cliente.setId(null);//certificar q é uma insercao e nao alteracao
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente cliente)
	{
		Cliente newCliente = find(cliente.getId());
		updateData(newCliente, cliente);
		return clienteRepository.save(newCliente);
	}

	private void updateData(Cliente newCliente, Cliente cliente)
	{
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public void delete(Integer categoryId)
	{
		find(categoryId);
		try
		{
			clienteRepository.deleteById(categoryId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

	public List<Cliente> findAll()
	{
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest
			.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente cliFromDTO(ClienteDTO clienteDTO)
	{
		return new Cliente(clienteDTO.getId(), clienteDTO.getName(), clienteDTO.getEmail(), null,
			null, null);
	}

	public Cliente cliFromDTO(ClienteNewDTO clienteNewDTO)
	{
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),
			clienteNewDTO.getCpfOuCnpj(),
			TipoCliente.getSafeTipoCliente(clienteNewDTO.getTipoCliente()), pe.encode(clienteNewDTO.getSenha()));

		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
			clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli,
			cidade);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNewDTO.getTelefone1());

		if (clienteNewDTO.getTelefone2() != null)
		{
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}

		if (clienteNewDTO.getTelefone3() != null)
		{
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}

		return cli;
	}


}
