package com.alvaroyraul;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Bote {

    //TODO: El tiempo de demora que cada bote tarda en contar a los pasajeros será aleatorio y va 
    // de los 2 a los 6 segundos . Thread.sleep(2000); //Demora un proceso 2 segundos
    private static final int MAXSLEEP = 6000;
    private static final int MINSLEEP  = 2000;

    //TODO: Hay 20 botes, cada bote tendrá un identificador que irá de B00 a B19.
    public static final int TOTALBOTES = 20;

    //TODO: Las personas saltaban a los botes y los liberaban para llegar al oceano, 
    // hasta pasado un tiempo, no se hacía el recuento de personas que viajaban en el bote. 
    // Podían ir de 1 a 100 personas. En nuestro simulador, cada bote se gestiona de forma autónoma 
    // generará un número aleatorio de personas.
    private static final int MAXPERSONAS = 100;
    private int personas;
    private int mujeres;
    private int hombres;
    private int ninios;
    private String nbote;

    // Lombok generará el constructor completo con @AllArgsConstructor

    // Metodo para simular la carga de un único bote y devolver sus datos
    public static Bote cargarBote(int i) {
        int numPersonas = (int) (Math.random() * MAXPERSONAS) + 1;
        int mujeres = (int) (Math.random() * (numPersonas + 1));
        int hombres = (int) (Math.random() * (numPersonas - mujeres + 1));
        int ninios = numPersonas - mujeres - hombres;

        String nbote = String.format("B%02d", i);

        // crear el objeto Botes con los datos generados
        Bote bote = new Bote(numPersonas, mujeres, hombres, ninios, nbote);

        // opcional: simular una demora corta (no bloqueante larga)
        try {
            Random rand = new Random();
            int delay = rand.nextInt(MAXSLEEP - MINSLEEP) + MINSLEEP; // valor entre MINSLEEP y MAXSLEEP inclusive
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return bote;
    }

    // se lo deberá comunicar al servicio de Emergencias.
}

