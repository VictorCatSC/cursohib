package com.victor.cursohibernate.resources.exceptions;

import com.victor.cursohibernate.services.exceptions.ObjectNotFoundException;
import javax.servlet.http.HttpServlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourcesExceptionHandler
{
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServlet request)
	{
		final Integer HttpStatusNotFound = HttpStatus.NOT_FOUND.value();

		StandardError err = new StandardError(HttpStatusNotFound, e.getMessage(), System.currentTimeMillis());
		return  ResponseEntity.status(HttpStatusNotFound).body(err);
	}
}
