package com.alvaroyraul;

// Servicio de Emergencias: invoca Botes, recibe datos y los muestra
public class ServicioEmergencia{
	public static void main(String[] args) {

		int maxpersonas = 0;
		int maxmujeres = 0;
		int maxhombres = 0;
		int maxninios = 0;

		System.out.println("Iniciando servicio de emergencias. Generando datos de botes...");

		for (int i = 0; i < Bote.TOTALBOTES; i++) {
			Bote bote = Bote.cargarBote(i);
			// usar toString generado por Lombok @Data
			System.out.println(bote);
			// contar totales
			maxpersonas += bote.getPersonas();
			maxmujeres += bote.getMujeres();
			maxhombres += bote.getHombres();
			maxninios += bote.getNinios();
		}

		

		System.out.println("Proceso finalizado.");
		System.out.println("Total personas rescatadas: " + maxpersonas);
		System.out.println("Total mujeres rescatadas: " + maxmujeres);
		System.out.println("Total hombres rescatadas: " + maxhombres);
		System.out.println("Total niños rescatadas: " + maxninios);
	}
}