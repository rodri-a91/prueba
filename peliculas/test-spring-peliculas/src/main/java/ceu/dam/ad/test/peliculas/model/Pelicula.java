package ceu.dam.ad.test.peliculas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "peliculas")
public class Pelicula {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String director;
	
	private Integer duracion;
	
	@Column(name ="año_estreno")
	private Integer estreno;
	
	@Transient
	private Integer edad;

}
