package ceu.api.user.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceu.api.user.server.dto.request.ChangePasswordRequestDto;
import ceu.api.user.server.dto.request.LoginRequestDto;
import ceu.api.user.server.dto.request.UserCreateRequestDto;
import ceu.api.user.server.dto.response.UserResponseDto;
import ceu.dam.ad.model.User;
import ceu.dam.ad.services.DuplicateUserException;
import ceu.dam.ad.services.UserException;
import ceu.dam.ad.services.UserNotFoundException;
import ceu.dam.ad.services.UserService;
import ceu.dam.ad.services.UserUnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/user")
@RestController
public class UserController {
	final UserService service;
	final ModelMapper mapper;

	public UserController(UserService service) {
		this.service = service;
		this.mapper = new ModelMapper();
	}

	@GetMapping("/{id}")
	public UserResponseDto getUser(@PathVariable Long id) throws UserNotFoundException, UserException {

		return mapper.map(service.getUser(id), UserResponseDto.class);

	}

	@PostMapping
	public UserResponseDto createUser(@RequestBody UserCreateRequestDto user)
			throws DuplicateUserException, UserException {
		User u = mapper.map(user, User.class);

		return mapper.map(service.createUser(u), UserResponseDto.class);

	}

	@Operation(summary = "Cambio de contraseña", description = "Permite cambiar la contraseña de un usuario")
	@PutMapping("/{id}")
	public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto dto)
			throws UserNotFoundException, UserUnauthorizedException, UserException {
		service.changePassword(id, dto.getOldPass(), dto.getNewPass());

	}

	@PostMapping("/login")
	public UserResponseDto login(@RequestBody LoginRequestDto request)
			throws UserNotFoundException, UserUnauthorizedException, UserException {
		User u = service.login(request.getLogin(), request.getPassword());
		return mapper.map(u, UserResponseDto.class);
	}

}
