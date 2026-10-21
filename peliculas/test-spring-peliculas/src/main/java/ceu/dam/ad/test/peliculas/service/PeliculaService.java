package ceu.dam.ad.test.peliculas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ceu.dam.ad.test.peliculas.model.Pelicula;
import ceu.dam.ad.test.peliculas.repositories.PeliculaRepository;

@Service
public class PeliculaService {

	private final PeliculaRepository repo;

	PeliculaService(PeliculaRepository repo) {
		this.repo = repo;
	}

	public List<Pelicula> consultarPeliculas() {

		return repo.findAll();

	}

	public Pelicula consultarPelicula(Long id) throws PeliculaNotFoundException {
		Optional<Pelicula> peli = repo.findById(id);

//		if (peli.isPresent()) return peli.get();
//		
//		throw new PeliculaNotFoundException("No existe película con el id " + id);

		return peli.orElseThrow(() -> new PeliculaNotFoundException("No existe película con el id " + id));
	}

	public List<Pelicula> consultarPeliculasDirector(String director, Integer año) {
//		return repo.findAll().stream().filter(p -> p.getDirector().equals(director)).toList();
		return repo.findByDirectorContainsAndEstrenoEquals(director, año);

	}

//	Filtro con una palabra para todas las peliculas que contengan en titulo o direccion

	public List<Pelicula> consultarPeliculas(String filtro) {
		return repo.findByTituloContainsOrDirectorContains(filtro, filtro);
	}

	public List<Pelicula> consultarPeliculasAños(Integer cantAños) {
		Integer fecha = LocalDate.now().minusYears(cantAños).getYear();
		return repo.findByEstrenoIsAfter(fecha);

	}

	public Pelicula crearPelicula(Pelicula pelicula) {
		return repo.save(pelicula);
	}

	public Pelicula actualizarPelicula(Pelicula pelicula) {
		return repo.save(pelicula);
	}

	public void borrarPelicula(Long id) {
		repo.deleteById(id);
	}

}
