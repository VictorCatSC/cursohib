package com.victor.cursohibernate.resources;

import com.victor.cursohibernate.DTO.ClienteDTO;
import com.victor.cursohibernate.domain.Cliente;

import com.victor.cursohibernate.services.ClienteService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource
{
	@Autowired
	private ClienteService service;

	//@GetMapping
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id)
	{
		Cliente obj = service.buscar(id);

		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaDTO, @PathVariable Integer id)
	{
		Cliente obj = service.catFromDTO(categoriaDTO);
		obj.setId(id);
		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id)
	{
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll()
	{
		List<Cliente> objList = service.findAll();
		List<ClienteDTO> dtoObjList = objList.stream().map(obj -> new ClienteDTO(obj))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(dtoObjList);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		Page<Cliente> objList = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> dtoObjList = objList.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(dtoObjList);
	}
}
