package com.alvaroyraul;

public class MainBote {
    // Constantes locales al proceso
    // defaults in milliseconds (2-6 seconds)
    public static final int MAXSLEEP = 6000; // ms
    public static final int MINSLEEP = 2000; // ms
    public static final int TOTALBOTES = 20;
    public static final int MAXPERSONAS = 100;

    public static final String JSON = "{\"nbote\":\"%s\",\"personas\":%d,\"mujeres\":%d,\"hombres\":%d,\"ninios\":%d}";
    public static final String B = "B";

    public static final int TRES = 3;
    public static final int DOS = 2;
    public static final int CUATRO = 4;

    public static void main(String[] args) {
        // Leer y validar argumentos: index [maxPersonas minSleep maxSleep nbotePrefix]
        int index = 0;
        int maxPersonas = MAXPERSONAS;
        int minSleep = MINSLEEP;
        int maxSleep = MAXSLEEP;
        String nbotePrefix = B;

        if (args.length > 0) {
            try { index = Integer.parseInt(args[0]); } catch (NumberFormatException e) { index = 0; }
        }
        if (args.length > 1) {
            try { maxPersonas = Integer.parseInt(args[1]); } catch (NumberFormatException e) { /* keep default */ }
        }
        if (args.length > 2) {
            try { minSleep = Integer.parseInt(args[2]); } catch (NumberFormatException e) { /* keep default */ }
        }
        if (args.length > 3) {
            try { maxSleep = Integer.parseInt(args[3]); } catch (NumberFormatException e) { /* keep default */ }
        }
        if (args.length > 4) {
            nbotePrefix = args[4];
        }

        if (maxSleep < minSleep) {
            int tmp = minSleep; minSleep = maxSleep; maxSleep = tmp;
        }

        // Aplicar sleep aleatorio entre minSleep y maxSleep (ambos en ms)
        int delay = minSleep + (int) (Math.random() * (maxSleep - minSleep + 1));
        try {
            Thread.sleep(delay); // e.g. Thread.sleep(2000); // demora 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Generar bote y emitir JSON
        Bote bote = Bote.cargarBote(index, maxPersonas, nbotePrefix);
        // Emitir JSON simple por stdout usando datos del Bote
        String json = String.format(JSON,
                bote.getNbote(), bote.getPersonas(), bote.getMujeres(), bote.getHombres(), bote.getNinios());
        System.out.println(json);
    }
}
