package com.alvaroyraul;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Generador de Informe.md en formato Markdown
public class GeneradorInforme {

    public static final String FMT = "dd/MM/yyyy 'a las' HH:mm:ss";
    public static final String SERVICIO_EMERGENCIAS = "# SERVICIO DE EMERGENCIAS\n\n";
    public static final String EJECUCION_REALIZADA = "Ejecución realizada el día %s\n\n";
    public static final String BOTE = "## Bote %s\n\n";
    public static final String TOTAL_SALVADOS = "- Total Salvados %d\n";
    public static final String MUJERES = "  - Mujeres %d\n";
    public static final String HOMBRES = "  - Varones %d\n";
    public static final String NINIOS = "  - Niños %d\n\n";
    public static final String TOTAL = "## Total\n\n";




    public static void generarInforme(String ruta, List<Bote> botes, int totalPersonas, int totalMujeres, int totalHombres, int totalNinios) throws IOException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FMT);
        String ahora = LocalDateTime.now().format(fmt);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ruta))) {
            w.write(SERVICIO_EMERGENCIAS);
            w.write(String.format(EJECUCION_REALIZADA, ahora));

            // Detalle por bote
            for (Bote b : botes) {
                w.write(String.format(BOTE, b.getNbote()));
                w.write(String.format(TOTAL_SALVADOS, b.getPersonas()));
                w.write(String.format(MUJERES, b.getMujeres()));
                w.write(String.format(HOMBRES, b.getHombres()));
                w.write(String.format(NINIOS, b.getNinios()));
            }

            // Totales
            w.write(TOTAL);
            w.write(String.format(TOTAL_SALVADOS, totalPersonas));
            w.write(String.format(MUJERES, totalMujeres));
            w.write(String.format(HOMBRES, totalHombres));
            w.write(String.format(NINIOS, totalNinios));
        }
    }

    public static void generarHTML() {
        throw new UnsupportedOperationException("Funcionalidad no implementada en esta versión");
    }
}
