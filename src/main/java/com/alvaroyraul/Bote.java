package com.alvaroyraul;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Bote {
    // Nota: constantes (TOTALBOTES, MAXPERSONAS, MINSLEEP, MAXSLEEP) se declaran en los mains
    private int personas;
    private int mujeres;
    private int hombres;
    private int ninios;
    private String nbote;

    // Lombok genera constructor y getters/setters
    // Método para simular la carga de un único bote
    public static Bote cargarBote(int i, int maxPersonas, int minSleep, int maxSleep, String nbotePrefix) {
        int numPersonas = (int) (Math.random() * maxPersonas) + 1;
        int mujeres = (int) (Math.random() * (numPersonas + 1));
        int hombres = (int) (Math.random() * (numPersonas - mujeres + 1));
        int ninios = numPersonas - mujeres - hombres;

        String nbote = String.format("%s%02d", nbotePrefix, i);

        // crear el objeto Botes con los datos generados
        Bote bote = new Bote(numPersonas, mujeres, hombres, ninios, nbote);

        // simular demora configurable
        try {
            int delay = (int) (Math.random() * (maxSleep - minSleep + 1)) + minSleep;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return bote;
    }

    // se lo deberá comunicar al servicio de Emergencias.
}

