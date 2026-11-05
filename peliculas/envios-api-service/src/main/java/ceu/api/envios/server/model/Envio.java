package ceu.api.envios.server.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "envios")
public class Envio {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String codigoSeguimiento;
	private LocalDateTime fechaRegistro;
	@Column(name = "peso_kg")
	private BigDecimal peso;
	private String estado;
	@Column(name = "temperatura_celsius")
	private BigDecimal temperatura;
	

}
