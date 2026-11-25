package AprenderFicheros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lección4 {
    public static void main(String[] args) throws IOException {

        double pTotal = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader("pedido.txt"))) {

            int nLinea = 0;
            String linea;
            while ((linea = br.readLine()) != null) {

                nLinea++;

                try {

                    String[] campos = linea.split(";");
                    int unidades = Integer.parseInt(campos[1]);
                    double precio = Double.parseDouble(campos[2]);
                    double mult = unidades * precio;

                    System.out.println(campos[0] + ": " + mult);

                    pTotal += mult;


                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Línea " + nLinea + " errónea: " + e.getMessage());
                }



            }

        }

        String total = String.format("%.2f", pTotal);
        System.out.println("Precio total: " + total);

    }
}
