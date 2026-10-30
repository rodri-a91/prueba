package ceu.api.user.server.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
	private String oldPass;
	private String newPass;

}
