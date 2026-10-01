package ceu.dam.ad.test.peliculas;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ceu.dam.ad.test.peliculas.model.Pelicula;
import ceu.dam.ad.test.peliculas.service.PeliculaService;

@SpringBootApplication
public class TestSpringPeliculasApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TestSpringPeliculasApplication.class, args);
		
		PeliculaService service = context.getBean(PeliculaService.class);
		
		List<Pelicula> result = service.consultarPeliculas();
		
		result.forEach(System.out::println);
	}

}
