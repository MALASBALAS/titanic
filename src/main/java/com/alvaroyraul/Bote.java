package com.alvaroyraul;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Bote {

    public static final String NBOTE = "%s%02d";
    // Nota: constantes (TOTALBOTES, MAXPERSONAS, MINSLEEP, MAXSLEEP) se declaran en los mains
    private int personas;
    private int mujeres;
    private int hombres;
    private int ninios;
    private String nbote;

    // Lombok genera constructor y getters/setters

    // Método para simular la carga de un único bote (solo genera datos)
    public static Bote cargarBote(int i, int maxPersonas, String nbotePrefix) {
        int numPersonas = (int) (Math.random() * maxPersonas) + 1;
        int mujeres = (int) (Math.random() * (numPersonas + 1));
        int hombres = (int) (Math.random() * (numPersonas - mujeres + 1));
        int ninios = numPersonas - mujeres - hombres;

        String nbote = String.format(NBOTE, nbotePrefix, i);

        return new Bote(numPersonas, mujeres, hombres, ninios, nbote);
    }

    // se lo deberá comunicar al servicio de Emergencias.
}

