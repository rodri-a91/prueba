package ceu.dam.ad.test.peliculas.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Estreno {

	@Id
	private String codEstreno;
	private LocalDate fecha;
	private String festival;

	@OneToOne(mappedBy = "estreno")
	private Pelicula pelicula;
}
