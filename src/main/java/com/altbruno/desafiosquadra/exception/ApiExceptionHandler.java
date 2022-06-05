package com.altbruno.desafiosquadra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Problema> camposInvalidos(MethodArgumentNotValidException ex) {
		Problema problema = new Problema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setMensagem("Uma ou mais propriedades estão inválidas");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	ResponseEntity<Problema> entidadeNaoEncontrada(EntityNotFoundException ex) {
		Problema problema = new Problema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setMensagem(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}

	@ExceptionHandler
	ResponseEntity<Problema> negocioException(NegocioException ex) {
		Problema problema = new Problema();
		problema.setStatus(HttpStatus.BAD_REQUEST.value());
		problema.setMensagem(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
}
