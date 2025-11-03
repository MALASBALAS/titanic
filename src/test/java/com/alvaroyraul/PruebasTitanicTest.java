package com.alvaroyraul;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PruebasTitanicTest {

    private static final String B00 = "B00";
    private static final String B01 = "B01";
    private static final String INFORME = "informe-test";
    private static final String MD = ".md";
    private static final String SERVICIO_EMERGENCIAS ="# SERVICIO DE EMERGENCIAS";
    private static final String EJECUCION_REALIZADA ="Ejecución realizada el día";
    private static final String BOTE_B00 ="## Bote B00";
    private static final String BOTE_B01 ="## Bote B01";
    private static final String TOTAL10 ="- Total Salvados 10";
    private static final String TOTAL5 ="- Total Salvados 5";
    private static final String TOTAL = "## Total";
    private static final String TOTAL15 ="- Total Salvados 15";

    private static final int DOS = 2;
    private static final int TRES = 3;
    private static final int CUATRO = 4;
    private static final int CINCO = 5;
    private static final int OCHO =8;
    private static final int DIEZ =10;
    private static final int QUINCE =15;

    @Test
    public void testGeneradorInformeFormatoBasico() throws Exception {
        // Preparar datos de ejemplo
        List<Bote> botes = new ArrayList<>();
        botes.add(new Bote(DIEZ, TRES, CINCO, DOS, B00));
        botes.add(new Bote(CINCO, 1, TRES, 1, B01));

        int totalPersonas = QUINCE;
        int totalMujeres = CUATRO;
        int totalHombres = OCHO;
        int totalNinios = TRES;

        File temp = File.createTempFile(INFORME, MD);
        temp.deleteOnExit();

        GeneradorInforme.generarInforme(temp.getAbsolutePath(), botes, totalPersonas, totalMujeres, totalHombres, totalNinios);

        String contenido = Files.readString(temp.toPath());
        assertTrue(contenido.contains(SERVICIO_EMERGENCIAS));
        assertTrue(contenido.contains(EJECUCION_REALIZADA));
        assertTrue(contenido.contains(BOTE_B00));
        assertTrue(contenido.contains(TOTAL10));
        assertTrue(contenido.contains(BOTE_B01));
        assertTrue(contenido.contains(TOTAL5));
        assertTrue(contenido.contains(TOTAL));
        assertTrue(contenido.contains(TOTAL15));
    }

    @Test
    public void testNumeroDeBotes() throws Exception {
        // Ejecutar solo un MainBote (index 0) y capturar su salida JSON
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            MainBote.main(new String[]{"0"});
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }

        String salida = baos.toString().trim();
        // Verificar que se generó JSON y contiene nbote B00 y campos numéricos
        assertTrue(salida.contains("\"nbote\":\"B00\""), "El JSON debe contener nbote B00");
        assertTrue(salida.contains("\"personas\":"), "El JSON debe contener el campo personas");
        assertTrue(salida.contains("\"mujeres\":"), "El JSON debe contener el campo mujeres");
        assertTrue(salida.contains("\"hombres\":"), "El JSON debe contener el campo hombres");
        assertTrue(salida.contains("\"ninios\":"), "El JSON debe contener el campo ninios");

        // Informe breve en la salida de pruebas
        System.out.println("MainBote generado: " + salida);
    }
}
