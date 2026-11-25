package AprenderFicheros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leccion3 {
    public static void main(String[] args) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader("compra.txt"))) {

            int tLineaAnterior = 0;
            int mLarga = 0;
            int nLineas = 0;
            String linea;
            while ((linea = br.readLine()) != null) {

                nLineas++;
                if (linea.isBlank()) {
                    continue;
                }

                System.out.println(nLineas + ": " + linea);
                if (linea.length() >= tLineaAnterior) {
                    mLarga = nLineas;
                    tLineaAnterior = linea.length();
                }

            }

            System.out.println("Total: " + nLineas + " líneas");
            System.out.println("La más larga es la " + mLarga);

        }

    }
}
