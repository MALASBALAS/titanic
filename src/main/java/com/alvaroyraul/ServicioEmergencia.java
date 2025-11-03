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


		public static final String INICIANDO = "Iniciando ServicioEmergencia: se lanzarán ";
		public static final String PROCESOS = " procesos...";
		public static final String JAVA_HOME = "java.home";
		public static final String BIN = "bin";
		public static final String JAVA = "java";
		public static final String JCP = "java.class.path";
		public static final String ARRANCAR_NO = "No se pudo arrancar proceso ";
		public static final String DOS_PUNTOS = ": ";
		public static final String RECIBIDO = "Recibido: ";
		public static final String PROCESO =  " (proceso ";
		public static final String PARENTESIS =  ")";
		public static final String ERROR_LEYENDO = "Error leyendo proceso ";
		public static final String INFORME_GENERADO = "Informe.md generado con éxito.";
		public static final String INFORME_NO_GENERADO = "No se pudo generar Informe.md: ";
		public static final String TOTAL_PERSONAS = "Servicio finalizado. Totales: personas=";
		public static final String TOTAL_MUJERES = ", mujeres=";
		public static final String TOTAL_HOMBRES = ", hombres=";
		public static final String TOTAL_NINIOS = ", niños=";
		public static final String PNB ="\"nbote\"\\s*:\\s*\"([^\"]+)\"";
		public static final String PNUM ="\"(personas|mujeres|hombres|ninios)\"\\s*:\\s*(\\d+)";
		public static final String PERSONAS ="personas";
		public static final String MUJERES ="mujeres";
		public static final String NINIOS = "ninios";
		public static final String HOMBRES ="hombres";
		public static final String ALVAROYRAUL = "com.alvaroyraul.MainBote";
		public static final String CP =  "-cp";

		public static final int DOS = 2;
		

	public static void main(String[] args) {
		System.out.println(INICIANDO + TOTAL_BOTES + PROCESOS);

		int total = TOTAL_BOTES;
		List<Bote> botes = new ArrayList<>();

		// Ruta al ejecutable java y classpath actuales
		String javaBin = System.getProperty(JAVA_HOME) + File.separator + BIN + File.separator + JAVA;
		String classpath = System.getProperty(JCP);

		// Lanzar todos los procesos hijos (paralelismo real)
		List<Process> procesos = new ArrayList<>();
		for (int i = 0; i < total; i++) {
			try {
				procesos.add(startChildProcess(javaBin, classpath, i));
			} catch (IOException e) {
				System.err.println(ARRANCAR_NO+ i + DOS_PUNTOS + e.getMessage());
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
						Bote bote = parseBoteFromJson(texto);
						botes.add(bote);
						System.out.println(RECIBIDO + bote.getNbote());
					}
				} else {
					System.err.println(MSG_ERROR + PROCESO+ i + PARENTESIS);
				}
			} catch (IOException | InterruptedException e) {
				System.err.println(ERROR_LEYENDO + i + DOS_PUNTOS + e.getMessage());
				Thread.currentThread().interrupt();
			}
		}

		// Sumar totales
		int totalPersonas = 0, totalMujeres = 0, totalHombres = 0, totalNinios = 0;
		for (Bote bote : botes) {
			totalPersonas += bote.getPersonas();
			totalMujeres += bote.getMujeres();
			totalHombres += bote.getHombres();
			totalNinios += bote.getNinios();
		}

		// Generar informe Markdown
		try {
			GeneradorInforme.generarInforme(OUTPUT_MARKDOWN, botes, totalPersonas, totalMujeres, totalHombres, totalNinios);
			System.out.println(INFORME_GENERADO);
		} catch (IOException e) {
			System.err.println(INFORME_NO_GENERADO + e.getMessage());
		}

		System.out.println(TOTAL_PERSONAS + totalPersonas + TOTAL_MUJERES + totalMujeres + TOTAL_HOMBRES + totalHombres + TOTAL_NINIOS + totalNinios);
	}

	// Parser muy simple para el JSON emitido por MainBote (sin dependencia externa)
	private static Bote parseBoteFromJson(String json) {
		// usar regex para extraer los campos incluso si hay texto adicional
		String nbote = null;
		int personas = 0, mujeres = 0, hombres = 0, ninios = 0;
		Pattern patronNombreBote = Pattern.compile(PNB);
		Pattern patronNumeros = Pattern.compile(PNUM);
		Matcher matcherNombreBote = patronNombreBote.matcher(json);
		if (matcherNombreBote.find()) nbote = matcherNombreBote.group(1);
		Matcher matcherNumeros = patronNumeros.matcher(json);
		while (matcherNumeros.find()) {
			switch (matcherNumeros.group(1)) {
				case PERSONAS -> personas = Integer.parseInt(matcherNumeros.group(DOS));
				case MUJERES -> mujeres = Integer.parseInt(matcherNumeros.group(DOS));
				case HOMBRES -> hombres = Integer.parseInt(matcherNumeros.group(DOS));
				case NINIOS -> ninios = Integer.parseInt(matcherNumeros.group(DOS));
			}
		}
		return new Bote(personas, mujeres, hombres, ninios, nbote);
	}

	// Lanzar un proceso hijo que ejecuta MainBote
	private static Process startChildProcess(String javaBin, String classpath, int index) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(
				javaBin,CP, classpath, ALVAROYRAUL,
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