package Tarea1_Files;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Tarea3 {

    private static final File baseDir = new File("datos/ud1/practice");

    public static void main(String[] args) {
        File frases = new File(baseDir, "frases.txt");

        try {

            // Frases con tilde
            try(FileOutputStream out = new FileOutputStream(frases)) {
                String contenido = "Programación\n Mañana\n Camión";
                out.write(contenido.getBytes("UTF-8"));
            }

            // Leer en UTF-8
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(frases), "UTF-8"))) {
              String line;
              while ((line = br.readLine()) != null) {
                  System.out.println(line);
              }
            }

            // Leer con Cp1252
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(frases), "Cp1252"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Conteo lineas, palabras, y vocales
            int lines = 0;
            int palabras = 0;
            int vocales = 0;

            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(frases), "UTF-8"))) {
               String line;
               String vocals = "aeiouáéíóúAEIOUÁÉÍÓÚ";

               while ((line = br.readLine()) != null) {
                   lines++;
                   palabras += line.trim().split("\\s+").length;

                   for (char c : line.toCharArray()) {
                       if(vocals.indexOf(c) != -1) {
                           vocales++;
                       }
                   }
               }
            }
            System.out.println("   Líneas: " + lines);
            System.out.println("   Palabras: " + palabras);
            System.out.println("   Vocales: " + vocales);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
