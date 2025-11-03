package com.alvaroyraul;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PruebasTitanicTest {

    @Test
    public void testGeneradorInformeFormatoBasico() throws Exception {
        // Preparar datos de ejemplo
        List<Bote> botes = new ArrayList<>();
        botes.add(new Bote(10, 3, 5, 2, "B00"));
        botes.add(new Bote(5, 1, 3, 1, "B01"));

        int totalPersonas = 15;
        int totalMujeres = 4;
        int totalHombres = 8;
        int totalNinios = 3;

        File temp = File.createTempFile("informe-test", ".md");
        temp.deleteOnExit();

        GeneradorInforme.generarInforme(temp.getAbsolutePath(), botes, totalPersonas, totalMujeres, totalHombres, totalNinios);

        String contenido = Files.readString(temp.toPath());
        assertTrue(contenido.contains("# SERVICIO DE EMERGENCIAS"));
        assertTrue(contenido.contains("Ejecución realizada el día"));
        assertTrue(contenido.contains("## Bote B00"));
        assertTrue(contenido.contains("- Total Salvados 10"));
        assertTrue(contenido.contains("## Bote B01"));
        assertTrue(contenido.contains("- Total Salvados 5"));
        assertTrue(contenido.contains("## Total"));
        assertTrue(contenido.contains("- Total Salvados 15"));
    }

    @Test
    public void testNumeroDeBotes() throws Exception {
        // Ejecutar el servicio (usa los valores por defecto)
        ServicioEmergencia.main(new String[]{});
        File informe = new File("Informe.md");
        String contenido = Files.readString(informe.toPath());
        long count = contenido.lines().filter(l -> l.startsWith("## Bote")).count();
        assertEquals(20, count, "El informe debería contener 20 botes");
    }
}
