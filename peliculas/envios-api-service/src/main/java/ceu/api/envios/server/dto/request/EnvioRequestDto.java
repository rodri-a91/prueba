package ceu.api.envios.server.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EnvioRequestDto {
	private BigDecimal peso;
	private String estado;
	private BigDecimal temperatura;

}
