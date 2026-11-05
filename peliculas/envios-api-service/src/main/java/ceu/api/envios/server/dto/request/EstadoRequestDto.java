package ceu.api.envios.server.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class EstadoRequestDto {

	private List<String> codigos;
	private String nuevoEstado;

}
