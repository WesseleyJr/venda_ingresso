package br.com.catedral.visitacao.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    List<String> erros = new ArrayList<>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        erros.add(error.getField() + ": " + error.getDefaultMessage());
	    }

	    ErroResposta erroResposta = new ErroResposta(
	        400,
	        "Campos inválidos, verifique se está correto",
	        LocalDateTime.now(),
	        erros
	    );

	    return ResponseEntity.badRequest().body(erroResposta);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
	    List<String> erros = new ArrayList<>();
	    ex.getConstraintViolations().forEach(cv -> {
	        erros.add(cv.getPropertyPath() + ": " + cv.getMessage());
	    });

	    ErroResposta erroResposta = new ErroResposta(
	        400,
	        "Erro de validação nos dados persistidos",
	        LocalDateTime.now(),
	        erros
	    );

	    return ResponseEntity.badRequest().body(erroResposta);
	}
	
	@ExceptionHandler(EmailException.class)
	private ResponseEntity<Object> handleEmailException(EmailException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(SenhaException.class)
	private ResponseEntity<Object> handleSenhaException(SenhaException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
}


