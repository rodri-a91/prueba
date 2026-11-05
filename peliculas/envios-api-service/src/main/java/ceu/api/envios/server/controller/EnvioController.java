package ceu.api.envios.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.api.envios.server.dto.request.EnvioRequestDto;
import ceu.api.envios.server.dto.request.EstadoRequestDto;
import ceu.api.envios.server.dto.response.EnvioResponseDto;
import ceu.api.envios.server.exception.EnvioNoEncontradoException;
import ceu.api.envios.server.exception.EstadoEnvioInvalidoException;
import ceu.api.envios.server.exception.PoliticaTransporteInvalidaException;
import ceu.api.envios.server.model.Envio;
import ceu.api.envios.server.service.EnviosService;

@RequestMapping("/envio")
@RestController
public class EnvioController {
	final EnviosService service;
	final ModelMapper mapper;

	public EnvioController(EnviosService service) {
		this.service = service;
		this.mapper = new ModelMapper();
	}

	@GetMapping
	public List<EnvioResponseDto> detectarEnviosEnRiesgoTermico(@RequestParam double limiteTemperatura) {
		List<Envio> envios = service.detectarEnviosEnRiesgoTermico(limiteTemperatura);
		List<EnvioResponseDto> enviosResponse = new ArrayList<>();
		for (Envio envio : envios) {
			EnvioResponseDto e = mapper.map(envio, EnvioResponseDto.class);
			enviosResponse.add(e);

		}
		return enviosResponse;
	}

	@PutMapping
	public long actualizarEstadoLoteReciente(@RequestBody EstadoRequestDto estado) throws EnvioNoEncontradoException {
		return service.actualizarEstadoLoteReciente(estado.getCodigos(), estado.getNuevoEstado());

	}

	@PostMapping
	public EnvioResponseDto registrarNuevoEnvio(@RequestBody EnvioRequestDto nuevoEnvio)
			throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		Envio envio = mapper.map(nuevoEnvio, Envio.class);
		envio = service.registrarNuevoEnvio(envio);
		return mapper.map(envio, EnvioResponseDto.class);
	}

	@GetMapping("/pesado")
	public EnvioResponseDto obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {

		return mapper.map(service.obtenerEnvioPesadoPrioritario(), EnvioResponseDto.class);

	}

}
