package com.victor.cursohibernate.services;

import com.victor.cursohibernate.domain.Cliente;
import com.victor.cursohibernate.domain.Pedido;
import com.victor.cursohibernate.repositoriesDAO.ClienteRepository;
import com.victor.cursohibernate.repositoriesDAO.PedidoRepository;
import com.victor.cursohibernate.services.exceptions.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService
{
	@Autowired
	private PedidoRepository repo;

	public Pedido buscar(Integer id)
	{
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
