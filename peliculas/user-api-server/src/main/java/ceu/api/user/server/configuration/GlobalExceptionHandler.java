package ceu.api.user.server.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ceu.dam.ad.services.DuplicateUserException;
import ceu.dam.ad.services.UserException;
import ceu.dam.ad.services.UserNotFoundException;
import ceu.dam.ad.services.UserUnauthorizedException;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = UserNotFoundException.class)
	public ResponseEntity<String> handle(UserNotFoundException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(exception = UserException.class)
	public ResponseEntity<String> handle(UserException e) {
		log.error("Error inesperado" + e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@ExceptionHandler(exception = DuplicateUserException.class)
	public ResponseEntity<String> handle(DuplicateUserException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(exception = UserUnauthorizedException.class)
	public ResponseEntity<String> handle(UserUnauthorizedException e) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handle(Exception e) {
		log.error("Error inesperado" + e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Error de validación de " + e.getFieldError().getField() + " - " + e.getFieldError().getDefaultMessage());
	}

}
