package ceu.dam.ad.services;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.dao.UserRepository;
import ceu.dam.ad.model.User;

@Service
public class UserServiceImp implements UserService {

	private final UserRepository repo;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	UserServiceImp(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public User createUser(User user) throws DuplicateUserException, UserException {
		try {
			logger.info("Creando usuario con estos datos: " + user);
//			1. Verificar que no existe usuario con ese email ni ese username. En caso contrario, lanzar DuplicateUserException
			Optional<User> emailConsul = repo.findByEmail(user.getEmail());
			Optional<User> usernameConsul = repo.findByUsername(user.getUsername());

			if (emailConsul.isPresent() || usernameConsul.isPresent()) {
				logger.debug("Se está intentando crear un usuario duplicado");
				throw new DuplicateUserException("Este usuario ya está registrado");
			}

//			2. Registrar el usuario en BBDD completando su fecha de alta y cifrando su password con SHA3-256
			user.setCreatedDate(LocalDate.now());
			user.setPassword(DigestUtils.sha3_256Hex(user.getPassword()));

			User idUser = repo.save(user);

//			3. Devolver el usuario con todos sus datos (incluyendo el ID) 
			logger.info("Usuario creado con ID " + idUser.getId());
			return idUser;

//			4. Si hay algún error, lanzará UserException con el origen	
		} catch (DataAccessException e) {
			logger.error("Error creando usuario en BBDD", e);
			throw new UserException("Error conectando a BBDD", e);
		}
	}

	@Override
	public void changePassword(Long idUser, String oldPassword, String newPassword)
			throws UserNotFoundException, UserUnauthorizedException, UserException {
		try {
			Optional<User> userConsul = repo.findById(idUser);

//			1. Si el usuario no existe con ese ID, lanzar UserNotFoundException

			if (userConsul.isEmpty())
				throw new UserNotFoundException("Este usuario no existe");

//			2. Verificar que la nueva password no es igual a la antigua. Si lo es, lanzar UserUnauthorizedException
			if (oldPassword.equals(newPassword))
				throw new UserUnauthorizedException("La contraseña es igual");

			User user = userConsul.get();

//			3. Verificar que la password antigua es correcta. Si no lo es, lanzar UserUnauthorizedException
			if (!DigestUtils.sha3_256Hex(oldPassword).equals(user.getPassword()))
				throw new UserUnauthorizedException("La contraseña no coincide");

//			4. Actualizar el nuevo password en el usuario cifrándolo previamente.
			user.setPassword(DigestUtils.sha3_256Hex(newPassword));
			repo.save(user);

		} catch (DataAccessException e) {
			throw new UserException("Error conectando a BBDD", e);
		}

	}

	@Override
	public User login(String login, String password)
			throws UserNotFoundException, UserUnauthorizedException, UserException {
		try {
			Optional<User> userConsul = repo.findByEmail(login);
//			1. Verificar que existe algún usuario con ese username o email. Si no es así, lanzar UserNotFoundException
			if (userConsul.isEmpty()) {
				userConsul = repo.findByUsername(login);
				if (userConsul.isEmpty())
					throw new UserNotFoundException("Este usuario no existe");
			}

			User user = userConsul.get();

//			2. Verificar que password es correcta. Si no lo es, lanzar UserUnauthorizedException 
			if (!DigestUtils.sha3_256Hex(password).equals(user.getPassword()))
				throw new UserUnauthorizedException("Password incorrecta");

//			3. Actualizamos fecha del último login. Si hay algún error aquí, registramos en el log, pero continuamos.
			try {
				user.setLastLoginDate(LocalDate.now());
				repo.save(user);

			} catch (Exception e) {
				System.err.println("Error actualizando fecha el último login");
				e.printStackTrace();
			}

//			4. Devolver el usuario con todos sus datos que ha realizado el login.
			return user;

		}

		catch (DataAccessException e) {
			throw new UserException("Error conectando a BBDD", e);
		}

	}

	@Override
	public User getUser(Long idUser) throws UserNotFoundException, UserException {
		try {
			Optional<User> userConsul = repo.findById(idUser);
			if (userConsul.isEmpty())
				throw new UserNotFoundException("Este usuario no existe");

			return userConsul.get();

		} catch (DataAccessException e) {
			throw new UserException("Error conectando a BBDD", e);
		}

	}

}
