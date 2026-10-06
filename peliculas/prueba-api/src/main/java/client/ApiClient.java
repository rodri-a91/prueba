package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;

import tools.jackson.databind.ObjectMapper;

public class ApiClient {

	private String urlBase;
	private HttpClient httpClient;

	public ApiClient(String urlBase) {
		this.urlBase = urlBase;
		httpClient = HttpClient.newHttpClient();
	}

	public Coche getCocheById(String id) throws CochesApiException {
		try {

			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + id)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			Integer status = response.statusCode();
			if (status == 200) {
//				ObjectMapper mapper = new ObjectMapper();
//				Coche coche = mapper.readValue(response.body(), Coche.class);
//				return coche;
				return new ObjectMapper().readValue(response.body(), Coche.class);
			} else if (status == 404) {
				throw new CochesNotFoundException("No existe el coche");
			}

			throw new CochesApiException("Código respuesta erróneo");

		} catch (Exception e) {
			e.printStackTrace();
			throw new CochesApiException("Error consultado api", e);
		}

	}

	public List<Coche> getCochesAll() throws CochesApiException {
		try {

			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			Integer status = response.statusCode();
			if (status == 200) {

				Coche[] cochesArray = new ObjectMapper().readValue(response.body(), Coche[].class);
				return Arrays.asList(cochesArray);
			}

			throw new CochesApiException("Código respuesta erróneo");

		} catch (Exception e) {
			e.printStackTrace();
			throw new CochesApiException("Error consultado api", e);
		}

	}

	public Coche createCoche(Coche coche) throws CochesApiException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String cocheJson = mapper.writeValueAsString(coche);

			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase)).POST(BodyPublishers.ofString(cocheJson))
					.build();

			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			Integer status = response.statusCode();
			if (status == 200) {

				return mapper.readValue(response.body(), Coche.class);
			}

			throw new CochesApiException("Código respuesta erróneo");

		} catch (Exception e) {
			e.printStackTrace();
			throw new CochesApiException("Error consultado api", e);
		}

	}

}
