package ceu.dam.ad.test.peliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ceu.dam.ad.test.peliculas.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
