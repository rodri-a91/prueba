package ceu.api.envios.server.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.api.envios.server.model.Envio;
import java.util.List;


public interface EnvioRepository extends JpaRepository<Envio, Long> {
	
	List<Envio> findByTemperaturaGreaterThan(BigDecimal temperatura);
	
	List<Envio> findByCodigoSeguimiento(String codigoSeguimiento);
	
	List<Envio> findAllByCodigoSeguimientoIn(List<String> codigoSeguimiento);
	
	List<Envio> findByEstado(String estado);

}
