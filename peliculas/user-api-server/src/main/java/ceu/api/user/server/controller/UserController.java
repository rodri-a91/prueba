package ceu.api.user.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.dam.ad.model.User;
import ceu.dam.ad.services.DuplicateUserException;
import ceu.dam.ad.services.UserException;
import ceu.dam.ad.services.UserNotFoundException;
import ceu.dam.ad.services.UserService;
import ceu.dam.ad.services.UserUnauthorizedException;

@RequestMapping("/user")
@RestController
public class UserController {
	final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) throws UserNotFoundException, UserException {
		return service.getUser(id);
	}

	@PostMapping
	public User createUser(@RequestBody User user) throws DuplicateUserException, UserException {
		return service.createUser(user);

	}

	@PutMapping
	public void changePassword(@RequestParam Long id, @RequestParam String oldPassword, @RequestParam String newPassword)
			throws UserNotFoundException, UserUnauthorizedException, UserException {
		service.changePassword(id, oldPassword, newPassword);

	}
	
	@GetMapping
	public User login(@RequestParam String login, @RequestParam String password) throws UserNotFoundException, UserUnauthorizedException, UserException {
		return service.login(login, password);
	}

}
