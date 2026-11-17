package ceu.api.envios.server.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ceu.api.envios.server.exception.EnvioNoEncontradoException;
import ceu.api.envios.server.exception.EstadoEnvioInvalidoException;
import ceu.api.envios.server.exception.PoliticaTransporteInvalidaException;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = EnvioNoEncontradoException.class)
	public ResponseEntity<String> handle(EnvioNoEncontradoException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(exception = EstadoEnvioInvalidoException.class)
	public ResponseEntity<String> handle(EstadoEnvioInvalidoException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(exception = PoliticaTransporteInvalidaException.class)
	public ResponseEntity<String> handle(PoliticaTransporteInvalidaException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(exception = IllegalArgumentException.class)
	public ResponseEntity<String> handle(IllegalArgumentException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handle(Exception e) {
		log.error("Error inesperado" + e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

}
