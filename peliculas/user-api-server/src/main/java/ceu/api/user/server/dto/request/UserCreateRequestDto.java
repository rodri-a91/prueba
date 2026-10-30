package ceu.api.user.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserCreateRequestDto {
	private String username;
	private String email;
	private String name;
	@Schema(description = "Password sin cifrar")
	private String password;

}
