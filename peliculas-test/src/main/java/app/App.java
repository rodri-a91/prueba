package app;

import java.sql.SQLException;
import java.util.List;

import model.Pelicula;
import service.PeliculaService;

public class App {

	public static void main(String[] args) {
		PeliculaService service = new PeliculaService();
		try {
			List<Pelicula> peliculas = service.findAll();

			for (Pelicula pelicula : peliculas) {
				System.out.println(pelicula);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
