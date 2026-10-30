package ceu.api.user.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDto {
	@Schema(description = "username o email del usuario")
	private String login;
	private String password;

}
