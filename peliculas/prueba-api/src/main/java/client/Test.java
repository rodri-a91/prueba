package client;

public class Test {

	public static void main(String[] args) {
		ApiClient apiClient = new ApiClient("https://crudcrud.com/api/6c53464bd7f24e0e89cf6e1bd7c9841b/coches");
		
		String coche;
		try {
			coche = apiClient.getCocheById("6abbf7860ef6b103e8952258");
			System.out.println(coche);
		} catch (CochesApiException e) {
			e.printStackTrace();
		}
		

	}

}
