package com.victor.cursohibernate.services.validation;

import com.victor.cursohibernate.DTO.ClienteDTO;
import com.victor.cursohibernate.DTO.ClienteNewDTO;
import com.victor.cursohibernate.domain.Cliente;
import com.victor.cursohibernate.domain.enums.TipoCliente;
import com.victor.cursohibernate.repositoriesDAO.ClienteRepository;
import com.victor.cursohibernate.resources.exception.FieldErrorMessage;
import com.victor.cursohibernate.services.validation.utils.BR;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>
{

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteUpdate ann)
	{
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context)
	{
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldErrorMessage> list = new ArrayList<>();

		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());

		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldErrorMessage("email", "Email já existente"));
		}

		for (FieldErrorMessage e : list)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}


