package AprenderFicheros;

import java.io.*;

public class Leccion2 {
    public static void main(String[] args) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("compra.txt", true))) {

            bw.write("pan");
            bw.newLine();
            bw.write("leche");
            bw.newLine();
            bw.write("huevos");

        }

    }

}
