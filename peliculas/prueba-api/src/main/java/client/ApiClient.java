package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ApiClient {

	private String urlBase;
	private HttpClient httpClient;

	public ApiClient(String urlBase) {
		this.urlBase = urlBase;
		httpClient = HttpClient.newHttpClient();
	}

	public String getCocheById(String id) throws CochesApiException {
		try {

			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase+"/"+id)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			Integer status = response.statusCode();
			if (status == 200) {
				return response.body();
			}
			else if (status == 404) {
				throw new CochesNotFoundException("No existe el coche");
			}
			
			throw new CochesApiException("Código respuesta erróneo");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CochesApiException("Error consultado api", e);
		}

	}
}
