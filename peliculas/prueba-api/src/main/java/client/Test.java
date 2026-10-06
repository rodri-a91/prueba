package client;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		ApiClient apiClient = new ApiClient("https://ca8bbd78e1d8beb2b210.free.beeceptor.com/api/coches");
		
		Coche coche;
		try {
			coche = apiClient.getCocheById("52e45e5ab0333e3a30c1");
			System.out.println(coche);

			System.out.println("///TODOS LOS COCHES///");
			List<Coche> coches = apiClient.getCochesAll();
			coches.forEach(System.out::println);
			
			Coche cocheParaCrear = new Coche();
	        cocheParaCrear.setCoche("Toyota");
	        cocheParaCrear.setModelo("Corolla");
	        cocheParaCrear.setColor("Rojo");

	        // 2. Llamar al método
	        Coche cocheResultado = apiClient.createCoche(cocheParaCrear);

	        // 3. Mostrar el resultado por consola
	        System.out.println("Coche creado con éxito:");
	        System.out.println("ID: " + cocheResultado.getId());
	        System.out.println("Coche/Marca: " + cocheResultado.getCoche());
	        System.out.println("Modelo: " + cocheResultado.getModelo());
	        System.out.println("Color: " + cocheResultado.getColor());
			
			
		} catch (CochesApiException e) {
			e.printStackTrace();
		}
		

	}

}
