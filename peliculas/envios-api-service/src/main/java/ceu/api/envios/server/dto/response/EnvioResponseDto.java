package ceu.api.envios.server.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvioResponseDto {
	private String codigoSeguimiento;
	private LocalDateTime fechaRegistro;
	private BigDecimal peso;
	private String estado;
	private BigDecimal temperatura;
}
