package ceu.dam.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findById(Long id);

}
