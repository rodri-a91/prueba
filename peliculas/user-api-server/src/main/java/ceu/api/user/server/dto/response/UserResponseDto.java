package ceu.api.user.server.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
	private String username;
	private String email;
	private String name;
	private Long id;
}
