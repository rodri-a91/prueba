package ceu.dam.ad.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String name;
	private String email;
	private String password;
	
	@Column(name = "created_date")
	private LocalDate createdDate;
	@Column(name = "last_login_date")
	private LocalDate lastLoginDate;

}
