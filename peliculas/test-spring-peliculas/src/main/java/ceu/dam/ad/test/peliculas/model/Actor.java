package ceu.dam.ad.test.peliculas.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "actores")
public class Actor {
	@Id
	private Long idActor;
	private String nombre;
	private Integer edad;
	
	@ManyToMany
	@JoinTable(name="peliculas_actores", 
	joinColumns = {@JoinColumn(name="id_actor")}, 
	inverseJoinColumns = {@JoinColumn(name="id_pelicula")})
	private List<Pelicula> peliculas;
}
