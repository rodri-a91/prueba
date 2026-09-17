package service;

import java.sql.SQLException;
import java.util.List;

import model.Pelicula;
import repository.PeliculaRepository;

public class PeliculaService {

	private PeliculaRepository repo;

	public PeliculaService() {
		repo = new PeliculaRepository();
	}

	public List<Pelicula> findAll() throws SQLException {
		return repo.findAll();

	}

}
