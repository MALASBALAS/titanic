package com.alvaroyraul;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Servicio principal: orquesta procesos hijos, recoge sus datos y genera Informe.md
public class ServicioEmergencia {
		// Constantes del Servicio (configurables)
		public static final int TOTAL_BOTES = 20; // número de botes
		public static final int PERSONAS_TOTALES_POR_BOTE = 100; // por bote
		public static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
		public static final String OUTPUT_MARKDOWN = "Informe.md";

		// Etiquetas del informe
		public static final String MD_TITLE = "SERVICIO DE EMERGENCIAS";
		public static final String MD_EXECUTION_PREFIX = "Ejecución realizada el día";
		public static final String LABEL_TOTAL_SALVADOS = "Total Salvados";
		public static final String LABEL_MUJERES = "Mujeres";
		public static final String LABEL_VARONES = "Varones";
		public static final String LABEL_NINOS = "Niños";

	public static void main(String[] args) {
		System.out.println("Iniciando ServicioEmergencia: se lanzarán " + TOTAL_BOTES + " procesos...");

		int total = TOTAL_BOTES;
		List<Bote> botes = new ArrayList<>();

		// Ruta al ejecutable java y classpath actuales
		String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");

		// Lanzar todos los procesos hijos (paralelismo real)
		List<Process> procesos = new ArrayList<>();
		for (int i = 0; i < total; i++) {
			try {
				procesos.add(startChildProcess(javaBin, classpath, i));
			} catch (IOException e) {
				System.err.println("No se pudo arrancar proceso " + i + ": " + e.getMessage());
			}
		}

		// Recolectar salidas y parsear
		for (int i = 0; i < procesos.size(); i++) {
			Process process = procesos.get(i);
			try {
				String texto = readProcessOutput(process);
				int exitVal = process.waitFor();
				if (exitVal == 0) {
					if (!texto.isEmpty()) {
						Bote b = parseBoteFromJson(texto);
						botes.add(b);
						System.out.println("Recibido: " + b.getNbote());
					}
				} else {
					System.err.println(MSG_ERROR + " (proceso " + i + ")");
				}
			} catch (IOException | InterruptedException e) {
				System.err.println("Error leyendo proceso " + i + ": " + e.getMessage());
				Thread.currentThread().interrupt();
			}
		}

		// Sumar totales
		int totalPersonas = 0, totalMujeres = 0, totalHombres = 0, totalNinios = 0;
		for (Bote b : botes) {
			totalPersonas += b.getPersonas();
			totalMujeres += b.getMujeres();
			totalHombres += b.getHombres();
			totalNinios += b.getNinios();
		}

		// Generar informe Markdown
		try {
			GeneradorInforme.generarInforme(OUTPUT_MARKDOWN, botes, totalPersonas, totalMujeres, totalHombres, totalNinios);
			System.out.println("Informe.md generado con éxito.");
		} catch (IOException e) {
			System.err.println("No se pudo generar Informe.md: " + e.getMessage());
		}

		System.out.println("Servicio finalizado. Totales: personas=" + totalPersonas + ", mujeres=" + totalMujeres + ", hombres=" + totalHombres + ", niños=" + totalNinios);
	}

	// Parser muy simple para el JSON emitido por MainBote (sin dependencia externa)
	private static Bote parseBoteFromJson(String json) {
		// usar regex para extraer los campos incluso si hay texto adicional
		String nbote = null;
		int personas = 0, mujeres = 0, hombres = 0, ninios = 0;
		Pattern pNb = Pattern.compile("\"nbote\"\\s*:\\s*\"([^\"]+)\"");
		Pattern pNum = Pattern.compile("\"(personas|mujeres|hombres|ninios)\"\\s*:\\s*(\\d+)");
		Matcher mNb = pNb.matcher(json);
		if (mNb.find()) nbote = mNb.group(1);
		Matcher mNum = pNum.matcher(json);
		while (mNum.find()) {
			switch (mNum.group(1)) {
				case "personas" -> personas = Integer.parseInt(mNum.group(2));
				case "mujeres" -> mujeres = Integer.parseInt(mNum.group(2));
				case "hombres" -> hombres = Integer.parseInt(mNum.group(2));
				case "ninios" -> ninios = Integer.parseInt(mNum.group(2));
			}
		}
		return new Bote(personas, mujeres, hombres, ninios, nbote);
	}

	// Lanzar un proceso hijo que ejecuta MainBote
	private static Process startChildProcess(String javaBin, String classpath, int index) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(
				javaBin, "-cp", classpath, "com.alvaroyraul.MainBote",
				String.valueOf(index), String.valueOf(PERSONAS_TOTALES_POR_BOTE)
		);
		pb.redirectErrorStream(true);
		return pb.start();
	}

	// Leer la salida completa de un proceso y devolverla como String (no espera el proceso)
	private static String readProcessOutput(Process process) throws IOException {
		StringBuilder output = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append('\n');
			}
		}
		return output.toString().trim();
	}


}