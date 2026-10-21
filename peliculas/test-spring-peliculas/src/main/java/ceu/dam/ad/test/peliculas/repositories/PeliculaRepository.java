package ceu.dam.ad.test.peliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ceu.dam.ad.test.peliculas.model.Pelicula;
import java.util.List;


public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
	
	List<Pelicula> findByDirectorContainsAndEstrenoEquals(String director, Integer año);
	
	List<Pelicula> findByTituloContainsOrDirectorContains(String filtro, String filtro2);
	
	List<Pelicula> findByEstrenoIsAfter(Integer estreno);

}
