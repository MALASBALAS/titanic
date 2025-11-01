package com.alvaroyraul;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Generador de Informe.md en formato Markdown
public class GeneradorInforme {
    public static void generarInforme(String ruta, List<Bote> botes, int totalPersonas, int totalMujeres, int totalHombres, int totalNinios) throws IOException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm:ss");
        String ahora = LocalDateTime.now().format(fmt);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ruta))) {
            w.write("# SERVICIO DE EMERGENCIAS\n\n");
            w.write(String.format("Ejecución realizada el día %s\n\n", ahora));

            // Detalle por bote
            for (Bote b : botes) {
                w.write(String.format("## Bote %s\n\n", b.getNbote()));
                w.write(String.format("- Total Salvados %d\n", b.getPersonas()));
                w.write(String.format("  - Mujeres %d\n", b.getMujeres()));
                w.write(String.format("  - Varones %d\n", b.getHombres()));
                w.write(String.format("  - Niños %d\n\n", b.getNinios()));
            }

            // Totales
            w.write("## Total\n\n");
            w.write(String.format("- Total Salvados %d\n", totalPersonas));
            w.write(String.format("  - Mujeres %d\n", totalMujeres));
            w.write(String.format("  - Varones %d\n", totalHombres));
            w.write(String.format("  - Niños %d\n", totalNinios));
        }
    }
}
