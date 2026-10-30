package ceu.dam.ad.test.peliculas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	private Integer duracion;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_director", nullable = false)
	private Director director;
	
	@Column(name ="año_estreno")
	private Integer añoEstreno;
	
	@OneToOne
	@JoinColumn(name = "cod_estreno")	
	private Estreno estreno;
	
	@JoinTable(name="peliculas_actores", 
	joinColumns = {@JoinColumn(name="id_pelicula")}, 
	inverseJoinColumns = {@JoinColumn(name="id_actor")})
	private List<Actor> actores;
	
	@Transient
	private Integer edad;

}
