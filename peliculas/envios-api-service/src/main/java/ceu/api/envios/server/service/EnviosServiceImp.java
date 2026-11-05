package ceu.api.envios.server.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ceu.api.envios.server.dao.EnvioRepository;
import ceu.api.envios.server.exception.EnvioNoEncontradoException;
import ceu.api.envios.server.exception.EstadoEnvioInvalidoException;
import ceu.api.envios.server.exception.PoliticaTransporteInvalidaException;
import ceu.api.envios.server.model.Envio;
import jakarta.transaction.Transactional;

@Service
public class EnviosServiceImp implements EnviosService {

	private final EnvioRepository repo;

	public EnviosServiceImp(EnvioRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura) {

		if (limiteTemperatura < -50 || limiteTemperatura > 50)
			throw new IllegalArgumentException("Límite de temperatura excedido");

		return repo.findByTemperaturaGreaterThan(BigDecimal.valueOf(limiteTemperatura));

	}

	@Override
	@Transactional
	public long actualizarEstadoLoteReciente(List<String> codigos, String nuevoEstado)
			throws EnvioNoEncontradoException {
		if (codigos.isEmpty())
			throw new IllegalArgumentException("La lista está vacía");

		List<Envio> envios = repo.findAllByCodigoSeguimientoIn(codigos);

		if (codigos.size() != envios.size())
			throw new EnvioNoEncontradoException("Hay algún pedido que no existe");

		envios = envios.stream().filter(e -> e.getFechaRegistro().isAfter(LocalDateTime.now().minusHours(24))).toList();

		for (Envio envio : envios) {
			envio.setEstado(nuevoEstado);
		}

		repo.saveAll(envios);

		return envios.size();
	}

	@Override
	public Envio registrarNuevoEnvio(Envio nuevoEnvio)
			throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {

		if (nuevoEnvio.getPeso().compareTo(BigDecimal.valueOf(1500)) > 0)
			throw new PoliticaTransporteInvalidaException("Peso no válido");

		if (nuevoEnvio.getEstado().equals("ENTREGADO"))
			throw new EstadoEnvioInvalidoException("Estado de envío no válido");

		nuevoEnvio.setCodigoSeguimiento(UUID.randomUUID().toString());

		return repo.save(nuevoEnvio);
	}

	@Override
	public Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		List<Envio> envios = repo.findByEstado("PENDIENTE");
		if (envios.isEmpty())
			throw new EstadoEnvioInvalidoException("No hay ningún envío pendiente");

		return envios.stream().max((a, b) -> a.getPeso().compareTo(b.getPeso())).get();
	}

}
