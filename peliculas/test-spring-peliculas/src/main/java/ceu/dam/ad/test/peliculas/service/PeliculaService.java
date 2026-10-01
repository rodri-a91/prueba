package ceu.dam.ad.test.peliculas.service;

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

		return peli.orElseThrow(() -> new PeliculaNotFoundException("No existe película con el id" + id));
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
