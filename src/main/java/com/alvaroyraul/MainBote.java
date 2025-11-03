package com.alvaroyraul;

public class MainBote {
    // Constantes locales al proceso
    public static final int MAXSLEEP = 6000;
    public static final int MINSLEEP = 2000;
    public static final int TOTALBOTES = 20;
    public static final int MAXPERSONAS = 100;

    public static final String JSON = "{\"nbote\":\"%s\",\"personas\":%d,\"mujeres\":%d,\"hombres\":%d,\"ninios\":%d}";
    public static final String B = "B";

    public static final int TRES = 3;
    public static final int DOS = 2;
    public static final int CUATRO = 4;

    public static void main(String[] args) {
        int index = 0;
    // Leer argumentos: index [maxPersonas minSleep maxSleep]
        int maxPersonas = MainBote.MAXPERSONAS;
        int minSleep = MainBote.MINSLEEP;
        int maxSleep = MainBote.MAXSLEEP;
        if (args.length > 0) {
            try { index = Integer.parseInt(args[0]); } catch (NumberFormatException e) { index = 0; }
        }
        String nbotePrefix = B;
        if (args.length > 1) {
            try { maxPersonas = Integer.parseInt(args[1]); } catch (NumberFormatException e) { /* keep default */ }
        }
        if (args.length > TRES) {
            try { minSleep = Integer.parseInt(args[DOS]); maxSleep = Integer.parseInt(args[TRES]); } catch (NumberFormatException e) { /* keep default */ }
        }
        if (args.length > CUATRO) {
            nbotePrefix = args[CUATRO];
        }

    // Generar bote y emitir JSON
        Bote bote = Bote.cargarBote(index, maxPersonas, minSleep, maxSleep, nbotePrefix);
        // Emitir JSON simple por stdout usando datos del Bote
        String json = String.format(JSON,
                bote.getNbote(), bote.getPersonas(), bote.getMujeres(), bote.getHombres(), bote.getNinios());
        System.out.println(json);
    }
}
